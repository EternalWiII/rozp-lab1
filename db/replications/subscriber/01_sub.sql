CREATE SUBSCRIPTION my_sub
CONNECTION 'host=db port=5432 user=user password=password dbname=traveler_db'
PUBLICATION my_pub;