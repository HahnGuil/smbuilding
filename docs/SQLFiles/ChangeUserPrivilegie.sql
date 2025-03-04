/*
For default implementation, all the news users when create is created with de RESIDENT role.
And you need of a ADMIN user to give de Roles MANAGER and BOARD to the users.

So the first thing that you need to do, is create a user and change his role for ADMIN.
You could use the end-point: localhost:8080/managerbuilding/auth/register (if you are using localhost:8080) and send the request using a json file like this:
Here is the JSON for a exemple of ADMIN user:
{
  "name": "ADMIN",
  "email": "admin@email.com",
  "password": "123456",
  "cpf": "12332112345",
  "tower": "1",
  "apartment": "101"
}

*/

-- After use the rigister end-point. You must to do a Select query for check the criation of the user.
SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, r.role_name
FROM users u
         JOIN user_roles ur ON u.id = ur.user_id
         JOIN roles r ON ur.role_id = r.id;

-- The next set is delete the others role of the User
DELETE FROM user_roles WHERE user_id = 'the id of the user that you want to promote to ADMIN';

--Now you add the role Admin to the user
INSERT INTO user_roles (user_id, role_id)
VALUES ('the id of the user that you want to promote to ADMIN', (SELECT id FROM roles WHERE role_name = 'ADMIN'));

/*
 When you complete the step before. You now can create a new users and give then the roles of manager e board.
 For to this you just need to login using the admin user, copy the token that the response give to you,
 Sendo the token in the request of tis url:
 localhost:8080/managerbuilding/users/assign-role/THE ID OF THE USER THAT YOU WANT DO GIVE DE ROLE/ROLE THAT YOU WANT

if you don't remember the roles, you can use:
 */
 SELECT * FROM roles;



