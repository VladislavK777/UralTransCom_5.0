package com.uraltranscom.dao.connection;

/*
 *
 * Класс соединения с БД
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 25.10.2017
 *
 * 13.11.2017
 *   1. Добавление хранения пароля в ZooKeeper
 *
 */

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ConnectionDB.class);

    private static ZooKeeper zk;
    private static ZkConnector zkc = new ZkConnector();

    // Определяем параметры БД{
    private static final String[] DATA_SOURCE = getDataFromZK();
    private static final String URL = DATA_SOURCE[0];
    private static final String USER = DATA_SOURCE[1];
    private static final String PASS = DATA_SOURCE[2];

    // Открываем соединение с БД
    private static Connection connection = getConnectDB();

    private static String[] getDataFromZK() {
        String[] data = new String[3];
        try {
            zkc.connect("localhost");
            zk = zkc.getZooKeeper();
            try {
                byte[] database = database = zk.getData("/zookeeper/DB_CONNECT/database", false, null);
                byte[] user = user = zk.getData("/zookeeper/DB_CONNECT/user", false, null);
                byte[] password = password = zk.getData("/zookeeper/DB_CONNECT/password", false, null);
                data[0] = new String(database, "UTF-8");
                data[1] = new String(user, "UTF-8");
                data[2] = new String(password, "UTF-8");

            } catch (IOException e) {
                logger.error("Ошибка получения данных из ZooKeeper.");
            }
        } catch (IOException | InterruptedException | KeeperException e) {
            logger.error("Ошибка подключения к ZooKeeper.");
        }
        return data;
    }

    private static Connection getConnectDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                logger.error("Ошибка закрытия соединения");
            }
            logger.error("Ошибка соединения");
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }
}
