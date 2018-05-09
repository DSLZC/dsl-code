package cn.dslcode.common.office.excel;

import cn.dslcode.common.core.json.JsonUtil;
import cn.dslcode.common.core.office.excel.ExcelUtil;
import cn.dslcode.common.core.office.excel.enums.Format;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dongsilin
 * @version 2018/4/27.
 */
@Slf4j
public class ExportTest {

    @Test
    public void test(){
        String[] jsons = {
            "{\"settleNo\":\"JZJS2018041800002\",\"state\":\"已完成\",\"sendAmount\":9,\"receiveAmount\":9,\"infoFee\":1,\"taxTotal\":26.16,\"payTotal\":27.16,\"originatorName\":\"铁甲小宝_承运\",\"addTime\":1524038241000,\"consignHandlerName\":\"铁甲小宝_托运\",\"consignHandleTime\":1524102123000}",
            "{\"settleNo\":\"JZJS2018040400002\",\"state\":\"已完成\",\"sendAmount\":990,\"receiveAmount\":900,\"infoFee\":100,\"taxTotal\":8610,\"payTotal\":8710,\"originatorName\":\"铁甲小宝_承运\",\"addTime\":1522826401000,\"consignHandlerName\":\"铁甲小宝_托运\",\"consignHandleTime\":1522826849000}",
            "{\"settleNo\":\"JZJS2018032900003\",\"state\":\"已完成\",\"sendAmount\":9,\"receiveAmount\":9,\"infoFee\":1,\"taxTotal\":26.16,\"payTotal\":27.16,\"originatorName\":\"铁甲小宝_承运\",\"addTime\":1522292804000,\"consignHandlerName\":\"铁甲小宝_托运\",\"consignHandleTime\":1522292912000}",
            "{\"settleNo\":\"JZJS2018032900001\",\"state\":\"已完成\",\"sendAmount\":11,\"receiveAmount\":11,\"infoFee\":1,\"taxTotal\":24.5,\"payTotal\":25.5,\"originatorName\":\"铁甲小宝_承运\",\"addTime\":1522292232000,\"consignHandlerName\":\"铁甲小宝_托运\",\"consignHandleTime\":1522292621000}" +
            "{\"settleNo\":\"WLJSD31026378249863168\",\"state\":\"已完成\",\"sendAmount\":9,\"receiveAmount\":8,\"infoFee\":1,\"taxTotal\":104.85,\"payTotal\":105.85,\"originatorName\":\"铁甲小宝_承运\",\"addTime\":1522133265000,\"consignHandlerName\":\"铁甲小宝_托运\",\"consignHandleTime\":1522133854000}"
        };
        List<SettleCentralExportVo> exportVoList = Stream.of(jsons).map(json -> JsonUtil.readJson(json, SettleCentralExportVo.class)).collect(Collectors.toList());
        log.info("{}", exportVoList);

        long tt = System.currentTimeMillis();
        ExcelUtil.export(Format._2007, SettleCentralExportVo.class, exportVoList, "D:\\导出excel");
        log.info("tttt = {}", System.currentTimeMillis() - tt);
    }

    @Test
    public void test1(){

        long tt = System.currentTimeMillis();

        List<SettleCentralExportVo> list = ExcelUtil.import_(new File("D:\\导出excel.xlsx"), 1, 0, SettleCentralExportVo.class);
        log.info("{}", list);

        log.info("tttt = {}", System.currentTimeMillis() - tt);
    }

}
