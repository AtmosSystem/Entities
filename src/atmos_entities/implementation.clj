(in-ns 'atmos-entities.core)

;------------------------------
; BEGIN General functions
;------------------------------

(defn defpersistence [db-map]
  (let [db-map (clojure.core/update db-map
                                    :classname #(str "com.mysql.cj.jdbc.Driver" %))]
    (sql/mysql db-map)))

(defn init-persistence [db-definition] (sql/defdb atmos-entities db-definition))

;------------------------------
; END - General functions
;------------------------------

;------------------------------
; BEGIN Entity functions
;------------------------------
(load "entities/core")
;------------------------------
; END - Entity functions
;------------------------------
