package util;

import com.uraltranscom.service.GetDistanceBetweenStations;
import com.uraltranscom.util.CommonPropertiesAccessor;
import com.uraltranscom.util.KeyMaster;
import com.uraltranscom.util.ZookeeperSettingHolder;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class testGetDistance {

    private DataSource dataSource;
    private ZookeeperSettingHolder zookeeperSettingHolder;

    @Before
    public void startup() throws Exception {
        dataSource = new DataSource();
        zookeeperSettingHolder = new ZookeeperSettingHolder();
        zookeeperSettingHolder.setCommonPropertiesAccessor(new CommonPropertiesAccessor());
        zookeeperSettingHolder.afterPropertiesSet();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(zookeeperSettingHolder.getDataBase());
        dataSource.setUsername(zookeeperSettingHolder.getUser());
        dataSource.setPassword(KeyMaster.dec(zookeeperSettingHolder.getPassword(), zookeeperSettingHolder.getSecretKey()));
    }

    @Test
    public void getConnect() throws Exception {
        System.out.println("Test Start");
        GetDistanceBetweenStations getDistanceBetweenStations = mock(GetDistanceBetweenStations.class);
        when(getDistanceBetweenStations.getDistanceBetweenStations("033409", "716607")).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), getDistanceBetweenStations.getDistanceBetweenStations("033409", "716607"));
    }
}
