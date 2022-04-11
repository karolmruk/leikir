db.createUser(
        {
            user: "application",
            pwd: "passwd",
            roles: [
                {
                    role: "readWrite",
                    db: "cdp"
                }
            ]
        }
);
