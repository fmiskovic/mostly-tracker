INSERT INTO projects (id, end_date, name, start_date)  VALUES (1, TO_DATE('17/12/2020', 'DD/MM/YYYY'), 'Test Project 1', TO_DATE('17/6/2019', 'DD/MM/YYYY'));
INSERT INTO projects (id, end_date, name, start_date)  VALUES (2, TO_DATE('17/12/2020', 'DD/MM/YYYY'), 'Test Project 2', TO_DATE('17/7/2019', 'DD/MM/YYYY'));
INSERT INTO projects (id, end_date, name, start_date)  VALUES (3, TO_DATE('17/12/2020', 'DD/MM/YYYY'), 'Test Project 3', TO_DATE('17/6/2019', 'DD/MM/YYYY'));

INSERT INTO entries (id, description, entry_date, time_spent, project_id) VALUES (1, 'desc 1', TO_DATE('16/11/2020', 'DD/MM/YYYY'), 3.33, 1);
INSERT INTO entries (id, description, entry_date, time_spent, project_id) VALUES (2, 'desc 2', TO_DATE('16/10/2020', 'DD/MM/YYYY'), 4.44, 1);
INSERT INTO entries (id, description, entry_date, time_spent, project_id) VALUES (3, 'desc 3', TO_DATE('16/09/2020', 'DD/MM/YYYY'), 4.44, 1);

INSERT INTO entries (id, description, entry_date, time_spent, project_id) VALUES (4, 'desc 3', TO_DATE('16/09/2020', 'DD/MM/YYYY'), 4.44, 2);
INSERT INTO entries (id, description, entry_date, time_spent, project_id) VALUES (5, 'desc 3', TO_DATE('16/09/2020', 'DD/MM/YYYY'), 4.44, 3);