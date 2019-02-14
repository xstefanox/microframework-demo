FROM fluent/fluentd:v1.3.3-1.0
RUN ["gem", "install", "--user-install", "fluent-plugin-elasticsearch", "--no-rdoc", "--no-ri", "--version", "3.2.1"]
COPY fluent.conf /fluentd/etc/
