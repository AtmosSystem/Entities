(ns atmos-entities.implementation
  (:require [korma.db :as sql]
            [korma.core :as orm]))

(def database (sql/mysql {:db "atmos-entities"
                          :user "root"
                          :password ""
                          :host "localhost"}))

(orm/defentity entities)

