--USER
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (1, 'user1@yopmail.com', 'EMPLOYEE', 'P@ssword1', 'user1', 'employee1');
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (2, 'user2@yopmail.com', 'EMPLOYEE', 'P@ssword1', 'user2', 'employee2');
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (3, 'manager1@yopmail.com', 'MANAGER', 'P@ssword1', 'user3', 'manager3');
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (4, 'manager2@yopmail.com', 'MANAGER', 'P@ssword1', 'user4', 'manager4');
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (5, 'engineer1@yopmail.com', 'ENGINEER', 'P@ssword1', 'user5', 'engineer5');
INSERT INTO user (id, email, ROLE_ID, userPassword, FIRST_NAME, LAST_NAME) VALUES (6, 'engineer2@yopmail.com', 'ENGINEER', 'P@ssword1', 'user6', 'engineer6');

--CATEGORY
INSERT INTO CATEGORY (ID, NAME) VALUES (1, 'APPLICATION_SERVICES');
INSERT INTO CATEGORY (ID, NAME) VALUES (2, 'BENEFITS_PAPERWORK');
INSERT INTO CATEGORY (ID, NAME) VALUES (3, 'HARDWARE_SOFTWARE');
INSERT INTO CATEGORY (ID, NAME) VALUES (4, 'PEOPLE_MANAGEMENT');
INSERT INTO CATEGORY (ID, NAME) VALUES (5, 'SECURITY_ACCESS');
INSERT INTO CATEGORY (ID, NAME) VALUES (6, 'WORKPLACES_FACILITIES');