# alter payment data so call to rewards_report stored procedure 
# returns a result set with two customers

UPDATE sakila.payment 
SET payment_date = (SELECT DATE_SUB(now(), INTERVAL 1 MONTH))
WHERE customer_id in (2,3);