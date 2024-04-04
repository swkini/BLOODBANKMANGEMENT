/* List all donors who have donated blood along with their blood types and the blood bank they donated to: */
USE BLOODBANKMANAGEMENT;
SELECT d.DONOR_NAME, b.BLOOD_TYPE, bb.BANK_NAME
FROM DONOR d, BLOOD b, BLOODBANK bb
WHERE d.DONOR_ID = b.DONOR_ID
AND b.BLOOD_BANK_ID = bb.BANK_ID;


/* List all patients along with their blood types and the hospital they are admitted to: */
USE BLOODBANKMANAGEMENT;
SELECT FIRST_NAME, LAST_NAME, HOSPITAL_NAME, BLOODTYPE
FROM PATIENTS;

/*Find out the doctors' phone numbers along with their names:*/
USE BLOODBANKMANAGEMENT;
SELECT d.DOCTOR_NAME, dp.DOCTOR_PHONENUMBER
FROM DOCTOR d, DOCTOR_PHONE dp
WHERE d.DOCTOR_ID = dp.DOCTOR_ID;

/*List all patients who have a specific blood type:*/
USE BLOODBANKMANAGEMENT;
SELECT *
FROM PATIENTS
WHERE BLOODTYPE = 'A+';

/*Find out which blood banks are delivering blood to which patients:*/
USE BLOODBANKMANAGEMENT;
SELECT p.FIRST_NAME, p.LAST_NAME, b.BANK_NAME, b.BANK_ADDRESS
FROM PATIENTS p, RBLOOD_DELIVERY r, BLOODBANK b
WHERE p.PATIENTS_ID = r.PATIENTS_ID
AND r.BANK_ID = b.BANK_ID;


