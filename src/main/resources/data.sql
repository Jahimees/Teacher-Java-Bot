-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS boughtCourses;
-- DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS tasks;
--
-- CREATE TABLE users (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   first_name VARCHAR(250) DEFAULT NULL,
--   last_name VARCHAR(250) DEFAULT NULL,
--   balance INT DEFAULT 0
-- );
--
-- CREATE TABLE boughtCourses (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   id_course INT NOT NULL,
--   id_user INT NOT NULL
-- );
--
-- CREATE TABLE courses (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   link VARCHAR(250) NOT NULL,
--   name VARCHAR(100) NOT NULL,
--   cost INT NOT NULL
-- );
--
CREATE TABLE tasks (
  id varchar(250) PRIMARY KEY,
  text CLOB NOT NULL,
  id_user INT DEFAULT NULL,
  date_created DATE default NULL,
  date_start DATE default NULL,
  date_end DATE default NULL,
  bonuses int default 0
);
