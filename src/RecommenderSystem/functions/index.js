const functions = require('firebase-functions');
const admin = require('firebase-admin');
const ContentBasedRecommender = require('content-based-recommender')
admin.initializeApp();

const db = admin.firestore();

exports.Recommender = functions.pubsub.schedule('0 7 * * *').onRun((context) => {
	const recommender = new ContentBasedRecommender({
		minScore: 0.1,
		maxSimilarDocuments: 100
	});
	let documents = [];
	let usersRef = db.collection('users');
	usersRef.get()
		.then(snapshot => {
			snapshot.forEach(doc => {
				let doc_id = doc.id
				let data = doc.data()
				let interest_array = data['interests'];
				let interest_string = "";
				
				interest_array.forEach(item => {
					interest_string = interest_string + item + " ";
				})

				documents.push({id:doc_id, content:interest_string});
				
			});

			// start training
			recommender.train(documents);

			snapshot.forEach(doc => {
				let id = doc.id
				const similarDocuments = recommender.getSimilarDocuments(id, 0, 5);
				usersRef.doc(id).update('similar_users', similarDocuments);
			});

			return null;
		})
		.catch(err => {
			console.log('Error getting documents', err);
		});
	return null;
});