FROM fluent/fluentd-kubernetes-daemonset:v1.11.5-debian-elasticsearch7-1.1
USER root
RUN fluent-gem install fluent-plugin-multi-format-parser
RUN fluent-gem install fluent-plugin-concat
RUN fluent-gem install fluent-plugin-detect-exceptions
RUN fluent-gem install fluent-plugin-rename-key
RUN fluent-gem install fluent-plugin-rewrite-tag-filter
