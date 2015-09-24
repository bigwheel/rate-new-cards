-- For H2 Database
create table users (
  id bigserial not null primary key,
  user_name varchar(512) not null,
  o_auth_type varchar(512) not null,
  o_auth_id varchar(512) not null,
  is_admin boolean not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
