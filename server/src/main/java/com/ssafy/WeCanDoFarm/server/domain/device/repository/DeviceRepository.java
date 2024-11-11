package com.ssafy.WeCanDoFarm.server.domain.device.repository;

import com.ssafy.WeCanDoFarm.server.domain.device.entity.Device;
import com.ssafy.WeCanDoFarm.server.domain.device.entity.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    public Device findFirstByDeviceStatus(DeviceStatus deviceStatus);

    @Modifying
    @Query("UPDATE Device d SET d.deviceStatus = :deviceStatus WHERE d.deviceId = :deviceId")
    public void updateDeviceStatusById(@Param("deviceId") Long deviceId, @Param("deviceStatus") DeviceStatus deviceStatus);
}
