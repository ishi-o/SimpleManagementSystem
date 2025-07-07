insert into jhomework.clients(cid, cname, phonenum, loc)
	values(10086, '赵', '13712341234', '辽宁大连'); 
insert into jhomework.clients(cid, cname, phonenum, loc)
	values(11053, '钱', '18224682468', '四川成都'); 
insert into jhomework.clients(cid, cname, phonenum, loc)
	values(10792, '孙', '15200360036', '江西南昌'); 
insert into jhomework.clients(cid, cname, phonenum, loc)
	values(20369, '李', '13812691269', '吉林吉林'); 
insert into jhomework.clients(cid, cname, phonenum, loc)
	values(10017, '周', '18273697369', '辽宁大连'); 


insert into jhomework.client_records(crid, cid, visit_date)
	values(90001, 10086, '2025-03-15');
insert into jhomework.client_records(crid, cid, visit_date)
	values(90002, 11053, '2024-10-17');
insert into jhomework.client_records(crid, cid, visit_date)
	values(90003, 10792, '2025-07-15');
insert into jhomework.client_records(crid, cid, visit_date)
	values(90004, 10017, '2025-06-19');
insert into jhomework.client_records(crid, cid, visit_date)
	values(90005, 20369, '2025-03-15');

insert into jhomework.employees(eid, ename, joindate)
	values(0216, '吴', '2005-06-06'); 
insert into jhomework.employees(eid, ename, joindate)
	values(0196, '郑', '1999-01-31'); 
insert into jhomework.employees(eid, ename, joindate)
	values(0219, '王', '2006-02-06'); 
insert into jhomework.employees(eid, ename, joindate)
	values(0232, '冯', '2010-03-06'); 
insert into jhomework.employees(eid, ename, joindate)
	values(0211, '陈', '2005-01-06'); 
insert into jhomework.employees(eid, ename, joindate)
	values(0370, '褚', '2015-06-06'); 

insert into jhomework.emp_records(eid, check_date)
	values(0216, '2025-07-10');
insert into jhomework.emp_records(eid, check_date)
	values(0219, '2025-07-10');
insert into jhomework.emp_records(eid, check_date)
	values(0370, '2025-07-10');
insert into jhomework.emp_records(eid, check_date)
	values(0216, '2025-07-09');
insert into jhomework.emp_records(eid, check_date)
	values(0232, '2025-07-08');
insert into jhomework.emp_records(eid, check_date)
	values(0196, '2025-07-10');
insert into jhomework.emp_records(eid, check_date)
	values(0219, '2025-07-09');
insert into jhomework.emp_records(eid, check_date)
	values(0211, '2025-07-10');

insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(26226, 0211, '2025-06-22');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(38496, 0211, '2025-06-22');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(42211, 0219, '2025-06-01');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(11779, 0232, '2025-06-10');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(35962, 0196, '2025-06-18');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(59624, 0370, '2025-04-06');
insert into jhomework.recv_packages(pid, recv_eid, proc_date)
	values(69843, 0216, '2025-04-22');

insert into jhomework.send_packages (pid, crid, fee, proc_date)
	values(10369, 90001, 50, '2025-04-22');
insert into jhomework.send_packages (pid, crid, fee, proc_date)
	values(10423, 90002, 50, '2025-07-10');
insert into jhomework.send_packages (pid, crid, fee, proc_date)
	values(10569, 90003, 15, '2024-12-30');
insert into jhomework.send_packages (pid, crid, fee, proc_date)
	values(10753, 90004, 30, '2025-06-18');

insert into jhomework.users(uid, username, password, login_status)
	values(235618, 'userman1', 'Abc@1234', false);
insert into jhomework.users(uid, username, password, login_status)
	values(312612, 'userman2', 'Abc@1234', false);
insert into jhomework.users(uid, username, password, login_status)
	values(436222, 'userman3', 'Abc@1234', false);
