create table projects (id int8 not null, end_date date not null, name varchar(255) not null, start_date date not null, primary key (id));
create table entries (id int8 not null, description varchar(255), entry_date date not null, time_spent float4 not null, project_id int8 not null, primary key (id));
create table summary (average_time_spent_per_day float4 not null, total_days int8 not null, total_time_spent float4 not null, project_id int8 not null, primary key (project_id));
create sequence project_entry_sequence start 1 increment 1;
create sequence project_sequence start 1 increment 1;
alter table entries add constraint FKk8n71u42svosu0oye4602q8nq foreign key (project_id) references projects on delete cascade;
alter table summary add constraint FK62wtswm7o4undycrl7v1atwhp foreign key (project_id) references projects;