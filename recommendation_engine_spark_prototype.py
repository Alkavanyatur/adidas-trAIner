# Import needed packages
from __future__ import absolute_import, print_function, division
import json
import numpy as np
import pandas as pd
import seaborn as sns
from pyspark.sql import SparkSession, Row
import pyspark.sql.functions as F
from pyspark.sql.types import IntegerType, StructType, StructField, StringType
from pyspark.ml import Pipeline
from pyspark.ml.evaluation import RegressionEvaluator
from pyspark.ml.recommendation import ALS
from pyspark.ml.tuning import CrossValidator, ParamGridBuilder
from pyspark.ml.feature import VectorAssembler

# Set Spark Session as entry point
spark = SparkSession.builder\
                    .appName("Simple recommendation engine using Spark MLlib")\
                    .config("spark.some.config.option", "config-value")\
                    .getOrCreate()\

# Read data from Firebase as performed in the model.py script
# and load into Spark cluster (or local) as a dataframe
schema = StructType([StructField("user_id", IntegerType(), False),
                     StructField("session_id", IntegerType(), False),
                     StructField("rating", IntegerType(), True)])

retrived_json_object = [json.dumps(retrived_json_object)]
jsonRDD = spark.sparkContext.parallelize(retrived_json_object)
df = spark.read.json(jsonRDD)
ratings_df = ratings_df.na.drop(how="any")

# Set model
als = ALS(userCol="user_id", itemCol="session_id", ratingCol="label", coldStartStrategy="drop", seed=0, nonnegative=True)

# Set considered parameter grid
paramGrid = ParamGridBuilder().addGrid(als.regParam, [0.5, 0.1, 0.05, 0.01]).addGrid(als.rank, [4, 8, 12]).build()

# Set evaluator
modelEvaluator = RegressionEvaluator(metricName="rmse")

# Set cross validator instance
crossval = CrossValidator(estimator=als,
                          estimatorParamMaps=paramGrid,
                          evaluator=modelEvaluator,
                          numFolds=10)

# Perform cross-validation
cvModel = crossval.fit(cv_data)

# Select best model and hyperparams
best_als_model = cvModel.bestModel
print("Best number of latent factors (rank parameter): " + str(best_als_model.rank))
print("Best value of regularization factor: " + str(best_als_model._java_obj.parent().getRegParam()))
print("Max Iterations: " + str(best_als_model._java_obj.parent().getMaxIter()))

# Make predictions on a random test subset obtained through randomSplit()
print("Predictions based on a random test subset:")
predictions = best_als_model.transform(test)
predictions.show(5)

# Evaluate model's performance on test (evaluate overfitting)
def overfitting_evaluation(predictions):
    # Model evaluation in test - ratings regression evaluation
    print("Model evaluation on test data:")
    predictions = predictions.na.drop()
    # RMSE
    rmse_evaluator = RegressionEvaluator(metricName="rmse", labelCol="label", predictionCol="prediction")
    rmse = rmse_evaluator.evaluate(predictions)
    print("Root-mean-square error (RMSE) = " + str(rmse))
    # MSE
    mse_evaluator = RegressionEvaluator(metricName="mse", labelCol="label", predictionCol="prediction")
    mse = mse_evaluator.evaluate(predictions)
    print("Mean-square error (MSE) = " + str(mse))
    # R2
    r2_evaluator = RegressionEvaluator(metricName="r2", labelCol="label", predictionCol="prediction")
    r2 = r2_evaluator.evaluate(predictions)
    print("r² metric = " + str(r2))
    # MAE
    mae_evaluator = RegressionEvaluator(metricName="mae", labelCol="label", predictionCol="prediction")
    mae = mae_evaluator.evaluate(predictions)
    print("Mean Absolute Error (MAE) = " + str(mae))

    return [rmse, mse, r2, mae]

random_test_eval = overfitting_evaluation(predictions)

# Generate recommendations for user
def profiles4userID(als_model, uid, limit=10):
    data = df.select("session_id").distinct().withColumn("user_id", F.lit(uid))
    rated_profiles = df.filter(df.user_id == uid).select("session_id", "user_id")
    predictions = als_model.transform(data).dropna().orderBy("prediction", ascending=False).limit(limit).select("session_id", "prediction")

print("Recommended contact for user " + str(uid) + ":")
profiles4userID(best_als_model, 1000)
