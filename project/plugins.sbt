resolvers += Resolver.url("scalasbt", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))  (Resolver.ivyStylePatterns)

addSbtPlugin("com.foursquare" % "spindle-codegen-plugin" % "1.7.0")

scalacOptions ++= Seq("-deprecation", "-unchecked")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")
