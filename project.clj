(defproject om-components "0.1.0-SNAPSHOT"
  :description "A set of Om components using Bootstrap markup"
  :url "http://example.com/FIXME"

  :source-paths ["src/clj" "src/cljs" "target/classes"]

  :dependencies [[org.clojure/clojure "1.6.0" :scope "provided"]
                 [org.clojure/clojurescript "0.0-2511" :scope "provided"]
                 [om "0.8.0-rc1" :scope "provided"]
                 [environ "1.0.0"]]

  :plugins [[lein-environ "1.0.0"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false} ["resources/public/js/app.js" "resources/public/js/out.js.map" "resources/public/js/out" "target"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :externs       ["react/externs/react.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns om-components.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :plugins [[com.cemerick/clojurescript.test "0.3.3"]
                             [lein-cljsbuild "1.0.4"]
                             [lein-figwheel "0.1.6-SNAPSHOT"]]

                   :figwheel {:http-server-root "public"
                              :port 3449
                              :css-dirs ["resources/public/css"]}

                   :dependencies [[com.cemerick/piggieback "0.1.3"]
                                  [weasel "0.4.2"]
                                  [figwheel "0.1.6-SNAPSHOT"]
                                  [ring "1.3.2"]
                                  [compojure "1.3.1"]
                                  [prismatic/dommy "1.0.0"]
                                  [leiningen "2.5.0"]]

                   :env {:is-dev true}

                   :cljsbuild {
                               :test-commands {"test" ["phantomjs" :runner "phantom/bind-polyfill.js" "target/cljs/testable.js"]}
                               :builds {
                                        :app {:source-paths ["env/dev/cljs"]}
                                        :test {:source-paths ["src/cljs" "src/cljs/om_components" "test/cljs"]
                                          ;; UNCOMMENT TO ENABLE AUTOTEST  :notify-command ["phantomjs" :cljs.test/runner "phantom/bind-polyfill.js" "target/cljs/testable.js"]
                                               :compiler {:output-to "target/cljs/testable.js"
                                                          :optimizations :whitespace
                                                          :preamble ["react/react.min.js"]
                                                          :pretty-print true}
                                               }
                                        }}}}

  :repositories [["snapshots" {:url "http://nexus.brightnorth.co.uk/content/repositories/snapshots" :password :env :username :env}]
                 ["releases" {:url "http://nexus.brightnorth.co.uk/content/repositories/releases" :password :env :username :env}]]

  :deploy-repositories [["s3-releases" {:url        "s3p://artifacts.brightnorth.co.uk/releases/"
                                        :username   :env/S3_RELEASES_KEY
                                        :passphrase :env/S3_RELEASES_SECRET}]])
