
DROP DATABASE IF EXISTS job_tinder;
CREATE DATABASE job_tinder;
-- DROP USER 'vprajago'@'localhost';
-- CREATE USER 'vprajago'@'localhost' IDENTIFIED BY 'vin007';
-- GRANT ALL PRIVILEGES ON * . * TO 'vprajago'@'localhost';
-- FLUSH PRIVILEGES;

USE job_tinder;

CREATE TABLE Students(
	studentID INT(11) PRIMARY KEY AUTO_INCREMENT,
	fullName VARCHAR(40) NOT NULL,
    username VARCHAR(20) UNIQUE KEY NOT NULL,
    pw VARCHAR(20) NOT NULL,
    school VARCHAR(40) NOT NULL,
	major VARCHAR(20) NOT NULL,
	gradYear VARCHAR(4) NOT NULL,  
    city VARCHAR(40) NOT NULL,
	email VARCHAR(40) NOT NULL,
	resumeLink VARCHAR(100) NOT NULL,
    imageURL VARCHAR(1000) NOT NULL DEFAULT ' ',
    gpa VARCHAR(10) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    sponsorship boolean NOT NULL,
    liked VARCHAR(10000) NOT NULL DEFAULT ' ',
    disliked VARCHAR(10000) NOT NULL DEFAULT ' '
    );
    
INSERT INTO Students (fullName, username, pw, school, major, gradYear, city, email, resumeLink, imageURL, gpa, phoneNumber, sponsorship, liked, disliked)
	VALUES  ('Emma Lautz', 'tester', 'test', 'USC', 'CSBA', '2019', 'Los Angeles', 'shingtat@usc.edu', 'google.com', 'http://www-scf.usc.edu/~csci201/images/emma_lautz.jpg', '4.0', '0123456789', TRUE, '', ''),
			('Kien Nguyen', 'kien', 'test', 'USC', 'CS', '2019', 'Los Angeles', 'kien.knguyen@usc.edu', 'google.com', 'http://www-scf.usc.edu/~csci201/images/kien_nguyen.jpg', '4.0', '0123456789', TRUE, '', '');
		
CREATE TABLE Employers(
	employerID INT(11) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE KEY NOT NULL,
    pw VARCHAR(20) NOT NULL,
	company VARCHAR(40) NOT NULL,
	email VARCHAR(40) NOT NULL,
    imageURL VARCHAR(1000) NOT NULL DEFAULT ' ',
    city VARCHAR(40) NOT NULL,
    industry VARCHAR(40) NOT NULL,
	sponsorship boolean NOT NULL,
    recruiter VARCHAR(40) NOT NULL,
    position VARCHAR(40) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    liked VARCHAR(10000) NOT NULL DEFAULT ' ',
    disliked VARCHAR(10000) NOT NULL DEFAULT' '
    );
    
INSERT INTO Employers(username, pw, company, email, imageURL, city, industry, sponsorship, recruiter, position, phoneNumber, liked, disliked)
	VALUES  ('intel', 'test', 'Intel',  'recruit@intel.com', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/1200px-Intel-logo.svg.png', 'Santa Clara', 'Technology', TRUE, 'Head Recruiter','Hardware Engineer','4087658080', '', ''),
		    ('google', 'test', 'Google',  'recruit@google.com', 'https://images.google.com/images/branding/googleg/1x/googleg_standard_color_128dp.png', 'Mountain View', 'Technology', TRUE, 'Head Recruiter','Software Engineer','0123456789', '', ''),
			('boa', 'test', 'Bank of America', 'recruit@bankofamerica.com','http://badcredit-v2.digitalbrandsinc.netdna-cdn.com/wp-content/uploads/BoA.jpg','Charlotte','Banking',FALSE, 'Head Recruiter','Financial Analyst','8004231000','',''),
            ('tesla', 'test', 'Tesla', 'recruit@tesla.com','http://www.carlogos.org/logo/Tesla-logo-2003-2500x2500.png','Palo Alto', 'Automotive',TRUE, 'Head Recruiter', 'Manufacturing Engineer', '8885183752','',''),
            ('cocacola', 'test', 'Coca-Cola', 'recruit@cocacola.com','https://www.fineprintnyc.com/images/blog/coke-logo/coke-logo-18.jpg', 'Atlanta', 'Food', FALSE, 'Head Recruiter','Merchandiser','8004382653','',''),
            ('pepsi','test','Pepsi', 'recruit@pepsi.com','http://ananiskan.com/wp-content/uploads/2015/06/Pepsi_logo_2014.svg1.png', 'Purchase', 'Food', FALSE, 'Head Recruiter', 'Merchandiser', '9142533150', '', ''),
            ('losangelestimes','test', 'Los Angeles Times','recruit@latimes.com', 'http://aptoscommunitynews.org/wp-content/uploads/2015/04/los_angeles_times_logo.png', 'Los Angeles', 'Media', FALSE, 'Head Recruiter', 'Editor', '2132375000','',''),
            ('nike', 'test', 'NIKE', 'recruit@nike.com', 'http://logok.org/wp-content/uploads/2014/03/Nike-logo-+-wordmark.png', 'Washington County', 'clothing', TRUE, 'Head Recruiter', 'Design Director', '8008066453','','');
            
CREATE TABLE Matches(
	matchID INT(11) PRIMARY KEY AUTO_INCREMENT,
	studentUsername VARCHAR(20) NOT NULL,
    employerUsername VARCHAR(20) NOT NULL,
	FOREIGN KEY (studentUsername) REFERENCES Students(username),
    FOREIGN KEY (employerUsername) REFERENCES Employers(username),
    matchDate DATETIME DEFAULT NOW()
    );
    


	
    
CREATE TABLE Chats(
	chatID INT(11) PRIMARY KEY AUTO_INCREMENT,
    matchID INT(11) NOT NULL,
    FOREIGN KEY (matchID) REFERENCES Matches(matchID),
    recordTime DATETIME NOT NULL DEFAULT NOW(),
    direction BOOLEAN NOT NULL,
    message TEXT NOT NULL
    );

    
    
    
    /*recordTime is the timestamp for the message */
    /*direction indicates whether it is the student a message to the employer or vice versa. True if it is student sending to employer*/
    
    
    
    
