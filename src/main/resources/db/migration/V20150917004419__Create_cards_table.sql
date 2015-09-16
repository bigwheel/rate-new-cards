-- For H2 Database
create table cards (
  id bigserial not null primary key,
  name varchar(512) not null,
  image_url varchar(512) not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
