(defproject api-tickets "1.0.0"
  :description "Tickets REST API"
  :url "http://materias.fi.uba.ar/7510/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :ring {:handler api-tickets/app}
   :dependencies [
    [clj-http "3.3.0"]
    [clojusc/twig "0.2.4"]
    [com.stuartsierra/component "0.3.1"]
    [compojure "1.5.1"]
    [dire "0.5.4"]
    [http-kit "2.2.0"]
    [leiningen-core "2.7.1" :exclusions [org.clojure/clojure]]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/tools.cli "0.3.5"]
    [ring/ring-core "1.5.0"]
    [ring-cors "0.1.12"]
    [ring/ring-defaults "0.2.1"]
    [ring/ring-devel "1.5.0"]
    [ring/ring-jetty-adapter "1.5.0"]
    [ring/ring-json "0.4.0"]
    [rage-db "1.0"]
    [ring/ring-mock "0.3.2"]
    ]
  :plugins [[lein-ring "0.9.7"]]
  :main ^:skip-aot api-tickets
  :aot :all
  :profiles
   {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}})