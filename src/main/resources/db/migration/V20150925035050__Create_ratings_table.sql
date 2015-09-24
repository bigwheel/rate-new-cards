-- For H2 Database
create table ratings (
  id bigserial not null primary key,
  user_id bigint not null,
  card_id bigint not null,
  score int not null,
  summary varchar(512) not null,
  description varchar(512) not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
