#!/usr/bin/env sh

./gradlew clean :application:build -Pgraal=true
unzip application/build/libs/application.jar -d build/libs/application

native-image --allow-incomplete-classpath -H:IncludeResources='META-INF/.*.json|META-INF/spring.factories|org/springframework/boot/logging/.*|kotlin/.*.kotlin_builtins' --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,org.springframework.core.io.VfsUtils,org.springframework.format.support.DefaultFormattingConversionService --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,org.springframework.core.io.VfsUtils,org.springframework.format.support.DefaultFormattingConversionService -H:ReflectionConfigurationFiles=graal/app.json,graal/boot.json,graal/framework.json,graal/netty.json,graal/log4j.json,graal/yavi.json -Dio.netty.noUnsafe=true -H:+ReportUnsupportedElementsAtRuntime -Dfile.encoding=UTF-8 -cp ".:$(echo build/libs/kofu-reactive-validation/BOOT-INF/lib/*.jar | tr ' ' ':')":build/libs/kofu-reactive-validation/BOOT-INF/classes com.sample.ApplicationKt[]
