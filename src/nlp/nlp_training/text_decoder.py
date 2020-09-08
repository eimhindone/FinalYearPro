# import tensorflow as tf
# from tensorflow import keras
# import pandas as pd
# import numpy as np
# from nltk.tokenize import word_tokenize
# from nltk.corpus import stopwords
# from nltk.stem.porter import PorterStemmer

# cleaned_train_sentences = []
# cleaned_test_sentences = []

# # stems the data
# def clean_sen(sen):
#     tokens = word_tokenize(sen)
#     words = [word for word in tokens if word.isalpha()]
#     words = [w for w in words if not w in stop_words]
#     stemmed = [porter.stem(word) for word in tokens]
#     return(stemmed)

# # turns sentence into list of ints and removes rare words
# def text_to_numbers(text, cutoff_for_rare_words = 1):
    
#     # Flatten list if sublists are present
#     if len(text) > 1:
#         flat_text = [item for sublist in text for item in sublist]
#     else:
#         flat_text = text
    
#     # get word freuqncy
#     fdist = nltk.FreqDist(flat_text)

#     # Convert to Pandas dataframe
#     df_fdist = pd.DataFrame.from_dict(fdist, orient='index')
#     df_fdist.columns = ['Frequency']

#     # Sort by word frequency
#     df_fdist.sort_values(by=['Frequency'], ascending=False, inplace=True)

#     # Add word index
#     number_of_words = df_fdist.shape[0]
#     df_fdist['word_index'] = list(np.arange(number_of_words)+1)

#     # replace rare words with index zero
#     frequency = df_fdist['Frequency'].values
#     word_index = df_fdist['word_index'].values
#     mask = frequency <= cutoff_for_rare_words
#     word_index[mask] = 0
#     df_fdist['word_index'] =  word_index
    
#     # Convert pandas to dictionary
#     word_dict = df_fdist['word_index'].to_dict()
    
#     # Use dictionary to convert words in text to numbers
#     text_numbers = []
#     for string in text:
#         string_numbers = [word_dict[word] for word in string]
#         text_numbers.append(string_numbers)  
    
#     return (text_numbers)

# # read data and store it
# reviews_df = pd.read_csv("Hotel_Reviews.csv")
# reviews_df["review"] = reviews_df["Negative_Review"] + reviews_df["Positive_Review"]
# reviews = reviews_df["Reviewer_Score"].apply(lambda x: 1 if x < 5 else 0)

# # break data into train and test data
# train_examples, test_examples = reviews_df["review"][0:-450000], reviews_df["review"][-450000:-1]
# train_score, test_score = reviews[0:-450000], reviews[-450000:-1]

# # clean the data remove stopwords and stem the words
# stop_words = stopwords.words('english')
# porter = PorterStemmer()

# for x in train_examples:
#     sent = clean_sen(x)
#     cleaned_train_sentences.append(sent)
    
# for x in test_examples:
#     sent = clean_sen(x)
#     cleaned_test_sentences.append(sent)
    
# # turn all the sentence words to ints
# text_numbers_train = text_to_numbers(cleaned_train_sentences)
# text_numbers_test = text_to_numbers(cleaned_test_sentences)

# # pad the data to 250 ints or trim to 250 ints
# text_numbers_train = keras.preprocessing.sequence.pad_sequences(text_numbers_train, value=0, padding='post', maxlen=250)
# text_numbers_test = keras.preprocessing.sequence.pad_sequences(text_numbers_test, value=0, padding='post', maxlen=250)

# # set up neural network
# model = keras.Sequential()
# model.add(keras.layers.Embedding(70000, 16))
# model.add(keras.layers.GlobalAveragePooling1D())
# model.add(keras.layers.Dense(16, activation="relu"))
# model.add(keras.layers.Dense(1, activation="sigmoid"))

# model.summary()

# # set up data loss
# model.compile(optimizer="adam", loss="binary_crossentropy", metrics=["accuracy"])

# # split train data into train and validation data
# x_val = text_numbers_train[:1000] # strings in numbers
# x_train = text_numbers_train[1000:] #  strings in numbers

# y_val = np.asarray(train_score[:1000]) # score
# y_train = np.asarray(train_score[1000:]) # score

# # train the model
# fitModel = model.fit(x_train, y_train, batch_size=512, epochs=40, validation_data=(x_val, y_val), verbose=1)

# print(len(text_numbers_test))
# print(len(test_score))

# # testing the data for accurasy
# results = model.evaluate(text_numbers_test, np.asarray(test_score))

# print(results)