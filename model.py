import numpy as np
import datetime as dt
import pandas as pd
import os
import requests

# Scikit-learn
from sklearn.model_selection import StratifiedShuffleSplit
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import learning_curve
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.naive_bayes import GaussianNB
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn import metrics
from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report
from sklearn.metrics import accuracy_score
from sklearn import linear_model
from sklearn.ensemble import VotingClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import GradientBoostingClassifier

from sklearn.metrics import log_loss
from sklearn.model_selection import learning_curve

import multiprocessing

# Keras with Tensorflow GPU backend
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
import keras.backend as K
from keras.wrappers.scikit_learn import KerasClassifier
# When the model will be selected this wrapper class will be used to perform cross-validation
# or even cross-validation with automatic parameter tunning (grid search) using scikit-learn.
from keras.models import Model
from keras import layers
from keras.engine.topology import Layer
from keras.layers.merge import concatenate
from keras import initializers
from keras.models import Sequential
from keras.layers import Activation, Dropout, Flatten, Dense, Input, BatchNormalization, LSTM, Embedding, Reshape, Bidirectional, GlobalMaxPool1D
from keras.layers.normalization import BatchNormalization
from keras.callbacks import EarlyStopping, ModelCheckpoint

# Declare connection with Firebase real-time database
from firebase import firebase
import json
firebase = firebase.FirebaseApplication('https://trainer-hack.firebaseio.com', None)

# Retrieve data from Firebase realtime database
try:
    result = firebase.get('/', 'sessions')
    print("New json object retrieved with code: " + str(result))
except:
    print("Connection error")

"""
This is just a test of the data gathering api.
The data used for the following models' training have been artificially
generated due to the necessity to have some data for this purpose.
We've faced the problem of no dataset availability for our FirebaseApplication
and no time to generate data in the format showed below.
"""

# Load sample dataset to perform machine learning with scikit-learn
# and some deep learning models for sequence/timeseries prediction using Keras.
df = pd.read_csv("sample_data_for_ml_testing.csv")
df['date'] = pd.to_datetime(df['date'])
#df.set_index('date', inplace=True)

x = df[['last_registered_capacity', 'mood', 'hours_of_sleep', 'objectiveType', 'weight_kg']].as_matrix()
y = df[['session_type']].as_matrix()

# Get a representative subset of the data for testing and use the rest of the data for cross-validation and training.
for train_index, test_index in StratifiedShuffleSplit(n_splits=1, test_size=0.15, random_state = 0).split(x,y):
    Xtrain = x[train_index,:]
    Xtest = x[test_index,:]
    Ytrain = y[train_index]
    Ytest = y[test_index]

# After performing a comparison in performance between different classic ML
# algorithms on scikit-learn, RandomForest has shown to be the most accurate one
model = RandomForestClassifier(n_estimators=200, max_depth=5,
                               random_state=0, class_weight='balanced')

#model = GradientBoostingClassifier(n_estimators=100, random_state=0)

model.fit(Xtrain, Ytrain)
Ypred = model.predict(Xtest)
#print('New prediction: ' + str(Ypred))
print('Accuracy score: ' + str(accuracy_score(Ytest, Ypred, normalize=True)))

"""
The new retrieved entry from Firebase would be preprocessed using json
utilities and a new prediction would be generated using the trained model.
Weekly or monthly depending on the gradient of change of the numerical values
evaluated, the whole model would be retrained with the historical data and the
last entries.
"""

# Let's assume the new prediction is given by:
new_prediction = Ypred[-1]
new_session_entry = '{\"session_type\": ' + str(new_prediction) + '}'
print(new_session_entry)

# Post new json object to Firebase Real Time Database
try:
    result = firebase.post('/predictions/', new_session_entry)
    print("Prediction loaded succesfully with code: " + result)

except:
    print("Connection error")
