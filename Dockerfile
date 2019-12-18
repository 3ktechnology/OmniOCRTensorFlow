FROM circleci/android:api-29

USER root
RUN useradd -ms /bin/bash admin

# copy all remaining files/folders in project directory to the container
COPY --chown=admin:admin . /app

# declare /app as working directory of image
WORKDIR /app
USER admin