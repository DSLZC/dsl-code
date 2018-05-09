package cn.dslcode.common.office.excel;

import cn.dslcode.common.core.date.DateUtil;
import cn.dslcode.common.core.office.excel.annotation.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算单导出的实体vo
 *
 * @author weimingwei
 * @version 2018-03-14 14:12
 **/
@Data
@ExportEntity(
    header = @ExcelHeader(title = "集中结算单数据", style = @CellStyle$(height = 25, bgColor = IndexedColors.TEAL, fontStyle = @FontStyle(size = 16, weight = Font.BOLDWEIGHT_BOLD))),
    head = @CellStyle$(height = 20, bgColor = IndexedColors.GREY_50_PERCENT, fontStyle = @FontStyle(size = 10, weight = Font.BOLDWEIGHT_BOLD, color = IndexedColors.WHITE)),
    body = @ExcelBody(showIndex = true, indexStyle = @CellStyle$(fontStyle = @FontStyle))
    )
@ImportEntity(showIndex = false)
public class SettleCentralExportVo {

    /**
     * 结算单号
     */
    @ExportField(title = "结算单号", sort = 0, style = @CellStyle$(width = 4200, height = 16, fontStyle = @FontStyle))
    @ImportField(title = "结算单号")
    private String settleNo;
    /**
     * 状态
     */
    @ExportField(title = "结算状态", sort = 1, style = @CellStyle$(width = 2500, fontStyle = @FontStyle))
    @ImportField(title = "结算状态")
    private String state;
    /**
     * 发货数量
     */
    @ExportField(title = "发货数量", sort = 2, pattern = "#,##0.000", style = @CellStyle$(width = 2500, align = CellStyle.ALIGN_RIGHT, fontStyle = @FontStyle))
    @ImportField(title = "发货数量", pattern = "#,##0.000")
    private int sendAmount;

    /** 实收数量 */
    @ExportField(title = "实收数量", sort = 3, pattern = "#,##0.000", style = @CellStyle$(width = 2500, align = CellStyle.ALIGN_RIGHT, fontStyle = @FontStyle))
    @ImportField(title = "实收数量", pattern = "#,##0.000")
    private BigDecimal receiveAmount;
    /**
     * 信息费
     */
    @ExportField(title = "信息费", sort = 4, pattern = "#,##0.00", style = @CellStyle$(width = 2500, align = CellStyle.ALIGN_RIGHT, fontStyle = @FontStyle))
    @ImportField(title = "信息费", pattern = "#,##0.00")
    private BigDecimal infoFee;

    /**
     * 含税金额
     */
    @ExportField(title = "含税金额", sort = 5, pattern = "#,##0.00", style = @CellStyle$(width = 2500, align = CellStyle.ALIGN_RIGHT, fontStyle = @FontStyle))
    @ImportField(title = "含税金额", pattern = "#,##0.00")
    private BigDecimal taxTotal;

    /**
     * 支付合计，单位为分，元x100
     */
    @ExportField(title = "支付合计", sort = 6, pattern = "#,##0.00", style = @CellStyle$(width = 2500, align = CellStyle.ALIGN_RIGHT, fontStyle = @FontStyle))
    @ImportField(title = "支付合计", pattern = "#,##0.00")
    private BigDecimal payTotal;

    /**
     * 发起人
     */
    @ExportField(title = "发起人", sort = 7, style = @CellStyle$(width = 4000, fontStyle = @FontStyle))
    @ImportField(title = "发起人")
    private String originatorName;

    /**
     * 发起时间
     */
    @ExportField(title = "发起时间", sort = 8, pattern = DateUtil.yyyyMMddHHmmss, style = @CellStyle$(width = 4500, fontStyle = @FontStyle))
    @ImportField(title = "发起时间", pattern = DateUtil.yyyyMMddHHmmss)
    private Date addTime;

    /**
     * 处理人
     */
    @ExportField(title = "结算单确认人", sort = 9, style = @CellStyle$(width = 4000, fontStyle = @FontStyle))
    @ImportField(title = "结算单确认人")
    private String consignHandlerName;

    /**
     * 处理时间
     */
    @ExportField(title = "确认时间", sort = 10, pattern = DateUtil.yyyyMMddHHmmss, style = @CellStyle$(width = 4500, fontStyle = @FontStyle))
    @ImportField(title = "确认时间", pattern = DateUtil.yyyyMMddHHmmss)
    private Date consignHandleTime;


}
