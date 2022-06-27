
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import auth

cred = credentials.Certificate(
    "college-management-d39bf-firebase-adminsdk-cglr0-f1f81ca5e8.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

try:
    user = auth.create_user(email="abcde@abcde.com", password="Abcdefg")
except Exception as e:
    print(e)
