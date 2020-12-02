CREATE DEFINER=`root`@`localhost` PROCEDURE `batch_insert_order`(in size int, out duration int)
BEGIN
DECLARE v_cnt int DEFAULT 0;
DECLARE i INT DEFAULT 0;
DECLARE batch_size int DEFAULT 5000;
declare v_start_time timestamp default current_timestamp();
declare v_end_time timestamp default current_timestamp();

SET autocommit = 0;

truncate TABLE `db`.`order`;
set v_start_time = current_timestamp();
WHILE v_cnt <= size DO

INSERT INTO `db`.`order`
(
    `user_id`,
    `status`,
    `pay_status`,
    `price`,
    `create_by`,
    `update_by`)
VALUES
(
    /*(FLOOR(RAND() * 2000) + current_timestamp(),*/
    0,
    0,
    0,
    RAND() * 50000,
    'JJ',
    'JJ');

SET i=i+1;
set v_cnt = v_cnt + 1;

IF I = BATCH_SIZE THEN
COMMIT;
SET I = 0;
END IF;

END WHILE;

commit;
set v_end_time = current_timestamp();
set duration = timestampdiff(second,v_start_time,v_end_time);
END