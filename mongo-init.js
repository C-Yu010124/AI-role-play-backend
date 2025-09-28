db = db.getSiblingDB('ai_db');
db.createUser({
  user: "test",
  pwd: "123456",
  roles: [ { role: "readWrite", db: "ai_db" } ]
});
