# Slideshow

To get started:

1. Run mvn clean package to build application image
2. Run docker-compose up in the project root folder

The application will be listening on port 8080, you can manually test the API by sending requests to the following endpoints:







 
To get started, refer to the following page on GitBook: [Suggested development environment](https://app.gitbook.com/@moment-team-as/s/development/project-tibet/suggested-development-environment).
> Please contact your supervisor if you don't have access to GitBook.

#### Environment

1.  Install Postgres v14
2.  Install JDK v11
3.  Install Maven

> If you use Windows:
>> Make sure you have Git Bash (git terminal for Windows)

## To start development locally

### Maven

* Obtain creds and login to https://milientsoftware.jfrog.io/ 
    * under User menu press Set Me Up and choose maven
    * provide password and Generate token (do not generate settings)
* Open `~/.m2/settings.xml`
* Add new `server` section as is:
  ```
  <server>
      <id>milient</id>
      <username>%jfrog-email%</username>
      <password>%jfrog-token%</password>
  </server>
  ```
  Note: you can use env variables as `<username>${env.USERNAME}</username>`
  
#### Profiles

Application support multiple profiles

* dev - stands for `development` env. By default `dev` profile is used and no coffee scripts pre compilation is done.
  * NB! During migration to new AWS secrets make sure to use one of this:
    * [Configure dev-role aws user](https://milient.atlassian.net/wiki/spaces/IS/pages/186253366/AWS+local+setup#Master-org-credential-setup-and-role-switching)
    * Or manually create `config-local.properties` that contains secrets for dev profile (you can find the secrets in git history)
* sandbox - TODO add config, enable compilation of coffeescripts when moved to new AWS in pom.xml
* demo - stands for `demo` env, used by clients time to time
* stage - stands for `stage` env, prod like env for final testing
* prod - stands for `prod` env, main production. For prod build cofeescrips should be pre-compiled with wro4j

Every porfile has corresponding 

* secrets entries in AWS secrets, see AppConfig.java and https://eu-north-1.console.aws.amazon.com/secretsmanager/listsecrets?region=eu-north-1
* pom.xml config, see tibet-project/pom.xml, line 749

### Env vars

Application can be configured via env vars, they will override corresponding application properties from the profile files or system properties from the profile files.

* ENVIRONMENT - stands for #{systemProperties.environment}
* AWS_TIBET_SM_ACCESS_KEY - needed for getting secrets from AWS
* AWS_TIBET_SM_SECRET_KEY - needed for getting secrets from AWS
* APP_MODE_READ_ONLY - stands for app.mode.readOnly, overrides it
* ANALYTICS_API_OPEN - stands for analytics.api.open, overrides it
* DB_HOST - stands for db host in context.xml for tomcat
* DB_PORT - stands for db port in context.xml for tomcat
* DB_SCHEMA - stands for db schema in context.xml for tomcat
* DB_USERNAME - stands for db username in context.xml for tomcat
* DB_PASSWORD - stands for db password in context.xml for tomcat

### Postgres

In Postgres (e.g. inside pgAdmin program):
1.  Create user "tibet" with password "tibet"
2.  Create a database "tibet" owned by user "tibet"

### Tibet

Next, in terminal:
* go to /tibet 

   *Run one of these, depending on your environment*:
  * run './mvnw clean install -Dskip.dockerPlugin=true' (to build the war file WITHOUT docker image)
  * run './mvnw clean install' (to build the war file WITH docker image)

* install ``wkhtmltopdf`` locally on your machine. Need this to build the project with the run_tibet_localhost command.

* run './fetch_demo_sql_and_update_server_localhost.sh' (to fill the local database with demo data)

* run './run_tibet_localhost.sh' (to start backend).

#### Versions 

Versions of the project should follow [Semantic Version](https://semver.org/) rules without patch part.
Project will not have standard `SNAPSHOT` and `RELEASE` postfixes, instead current actual version in the format `<major>.<minor>` will be defined in the pom files.

Every developer will be able to specify new upper version, in order to set new version one can run

`./mvnw versions:set-property -Dproperty=revision -DnewVersion=<new_major>.<new_minor>.<new_patch>`

or

`POM_VERSION=<new_major>.<new_minor>.<new_patch> make set-version`

GitHub action will do autoincrement of `<patch>` part of the version on PR merge to `develop` branch.

Autoincrement can be done with

`./mvnw clean validate -DbumpUpVersionPatch`

this code will not do any commits to the git.

#### Releases
Merge to the `master` branch is considered as new release.

During build process via GitHub actions, next steps will be done

* new version of the project will be set as `<current_version_from_pom_xml>`
* build artifacts(jars, war) will be published to [GitHub Packages](https://github.com/moment-team/mvn-internal-packages)
* new docker image pushed to ECR
* [infra-flux-release](https://github.com/moment-team/infra-flux-release) scans ECR for new released version and deploys it to kubernates prod cluster


#### Tests

Tests are executed with maven. They are split into 
* unit tests - test class file should end with `*Test.java`
* integration tests - test class file should end with `*IT.java`

Split is done via `maven-surefire-plugin` and maven profiles.

In order to run unit tests do

```
./mvnw clean install -P unit-test
```

for integration tests

```
./mvnw clean install -P integration-test
```

for puppeteers test, [see README for `end-to-end` module](end-to-end-tests/readme.md)

```
./mvnw clean install -P puppeteer-test
```

## Build with make

It's useful to combine all local script under single entrypoint, one can use [make](https://www.gnu.org/software/make/) for this.

Running `make` from root project folder will output all available commands.

For local build use `make build` from root project folder.

## Run with docker

One can run setup locally with docker and docker-compose.

In order to do that `.env` file should be created based on `.env_sample` with updated properties.

Running `docker-compose up -d` from root project folder will bring up all necessary containers.

## Publish docker images

Project is using [docker-maven-plugin](https://dmp.fabric8.io/), build of docker image is done on maven's `install` phase.

In order to tag and publish image run

```                           
./mvnw docker:tag -Ddocker.image.tag=${TIBET_VERSION} -pl tibet-server -DAWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} -DAWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} -DAWS_DEFAULT_REGION=eu-west-1

./mvnw docker:push -Dimage.tag=${TIBET_VERSION} -pl tibet-server -DAWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} -DAWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} -DAWS_DEFAULT_REGION=eu-west-1
```

## Pipelines

Current setup is using GitHub actions.


### GitHub actions

* `label` - sets some tag depending on where commit was done.
* `lokalisePushNewKeys` - used for updating i18n localization entries in external service - `lokalise`
* `migration-fix` - resolving DB migrations conflicts
* `sentry` - creates new release in Sentry
* `auto-back-merge-master-dev` - merges master to release branch and release to dev, needed when hotfixes done to master branch
* `auto-back-merge-release-dev` - merges release to dev, needed when fixes done to release branch
* `auto-merge-dev-to-release` - merges dev to release branch nightly, needed for automation and triggering puppeteer tests at night
* `auto-version-china` - automatically updates `tibet-common` version in china repo
* `auto-version-taiwan` - automatically updates `tibet-common` version in taiwan repo
* `increment-version.yml` - automatically increments version in main tibet pom.xml
* `build-and-deploy` - builds tibet and deploys pull request version if label `deploy` is set to PR, versions are read from description, notation is `oslo-version:pr-2681; db-version:internal-test-latest;`. No tests are run in this action.
* `build-and-test` - builds and runs unit and integration tests
* `scheduled-merge-dev-to-release` - scheduled merge from dev to release aka app pre release
* `scheduled-run-puppeteers-develop` - runs end to end tests nightly for develop branch
* `scheduled-run-puppeteers-release` - - runs end to end tests nightly for release branch

