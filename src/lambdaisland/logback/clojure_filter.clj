(ns lambdaisland.logback.clojure-filter
  (:gen-class
   :extends ch.qos.logback.core.filter.Filter
   :name lambdaisland.logback.ClojureFilter
   :state state
   :init init
   :constructors {[] []}
   :methods [[setExpression [String] void]])
  (:import [ch.qos.logback.core.spi FilterReply]))

(def ^:dynamic *event*)

(defonce last-error (atom nil))

(defn -init []
  [[] (atom "true")])

(defn -setExpression [this e]
  (reset! (.state this) e))

(defn expression [this]
  `(do
     (in-ns '~(symbol (namespace `_)))
     (let [~'event *event*
           ~'logger-name (.getLoggerName ~'event)
           ~'level (.getLevel ~'event)
           ~'message (.getMessage ~'event)
           ~'marker (.getMarker ~'event)
           ~'timestamp (.getTimeStamp ~'event)]
       ~(read-string (str "(do " @(.state this) ")")))))

(defn eval-event [this event]
  (if-let [ex @(.state this)]
    (binding [*event* event]
      (boolean (eval (expression this))))
    true))

(defn -decide [this event]
  (try
    (if (eval-event this event)
      FilterReply/NEUTRAL
      FilterReply/DENY)
    (catch Throwable t
      (reset! last-error t)
      FilterReply/DENY)))

#_
(compile 'lambdaisland.logback.clojure-filter)
