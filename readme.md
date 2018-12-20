Elasticsearch indexes are created using the Hibernate Search specification: the type cannot be different from the package.class of the indexed class.
Changing the package of the indexed class require to recreate the index in elasticsearch as well.
Definition of the index of the Venue class is in the persistence/src/test/resources folder.