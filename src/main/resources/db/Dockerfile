FROM liquibase:4.27.0
COPY ./changelog/. /liquibase/changelog/
ENTRYPOINT ["liquibase", "--changelog-file=changelog/db.changelog-master.yaml", "update"]