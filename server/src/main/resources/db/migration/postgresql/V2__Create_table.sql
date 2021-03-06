CREATE TABLE environment
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  profil character varying(40) REFERENCES T_AUTHORITY (name),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT environment_unique_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE softwaresuite
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT softwaresuite_id_unique_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE softwaresuite_environment
(
  environment_id integer NOT NULL REFERENCES environment (id),
  softwaresuite_id integer NOT NULL REFERENCES softwaresuite (id),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT softwaresuite_env_id_unique_key UNIQUE (environment_id, softwaresuite_id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE application
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  softwaresuite_id integer REFERENCES softwaresuite (id),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT application_unique_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE applicationversion
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  application_id integer NOT NULL REFERENCES application (id),
  blocked boolean DEFAULT false,
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT applicationversion_unique_key UNIQUE (code, application_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE trackingversion
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  applicationversion_id integer NOT NULL REFERENCES applicationversion (id),
  version integer,
  active boolean DEFAULT true,
  blocked boolean DEFAULT false,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT trackingversion_unique_key UNIQUE (code, applicationVersion_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE instance
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  application_id integer NOT NULL REFERENCES application (id),
  environment_id integer NOT NULL REFERENCES environment (id),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT instance_unique_key UNIQUE (code, application_id, environment_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE parametergrpt
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT parametergrpt_unique_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);

CREATE TYPE typeparameter AS ENUM ('APPLICATION', 'INSTANCE');
CREATE TABLE parameter
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(250),
  parametergroupment_id integer REFERENCES parametergrpt (id),
  application_id integer NOT NULL REFERENCES application (id),
  version integer,
  active boolean DEFAULT true,
  password boolean DEFAULT false,
  type character varying(40) NOT NULL,
  active_change_date date,
  change_user character varying(40),
  change_date date,
  CONSTRAINT parameter_unique_key UNIQUE (code, application_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE parametervalue
(
  id integer NOT NULL PRIMARY KEY,
  code character varying(40),
  label character varying(2500),
  oldvalue character varying(2500),
  environment_id integer NOT NULL REFERENCES environment (id),
  environment_code character varying(40),
  environment_label character varying(2500),
  trackingversion_id integer NOT NULL REFERENCES trackingversion (id),
  trackingversion_code character varying(40),
  trackingversion_label character varying(2500),
  parameter_id integer NOT NULL REFERENCES parameter (id),
  parameter_label character varying(2500),
  instance_id integer REFERENCES instance (id),
  instance_code character varying(40),
  instance_label character varying(2500),
  application_id integer NOT NULL REFERENCES application (id),
  application_code character varying(40),
  application_label character varying(2500),
  version integer,
  active boolean DEFAULT true,
  active_change_date date,
  change_user character varying(40),
  change_date date
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_application
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_softwaresuite
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_application_version
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_environment
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_instance
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_parameter
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_parameter_value
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_parameter_grpt
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE seq_version_tracking
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;