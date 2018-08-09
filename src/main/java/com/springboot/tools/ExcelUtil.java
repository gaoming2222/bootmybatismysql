package com.springboot.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.TextAnchor;

public class ExcelUtil {
	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(1, 256*21);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
    public static HSSFWorkbook getHSSFWorkbook1(String sheetName,String []title,String [][]values, HSSFWorkbook wb,DefaultXYDataset xydataset,String chartName,String sheetNameTmp) throws Exception{

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(1, 256*21);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        JFreeChart chart = createChart1(sheetNameTmp,xydataset);
        try {
            ChartUtilities.writeChartAsPNG(byteArrayOut, chart, 300, 400);
        } catch (IOException e) {
        }
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 8, (short) 5, (short) 16, (short) 42);
        anchor.setAnchorType(3);
        // 插入图片
        patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
        return wb;
    }
    

    public static JFreeChart createChart(String chartName,DefaultCategoryDataset  dataset){
		JFreeChart chart = ChartFactory.createLineChart("测试一下哈", "温度", "高程", dataset, PlotOrientation.VERTICAL, true, true, true);
		
		//chart的背景与提示
		CategoryPlot cp = chart.getCategoryPlot();
		cp.setBackgroundPaint(ChartColor.WHITE); // 背景色设置
		cp.setDomainGridlinePaint(Color.gray);
		cp.setDomainGridlinesVisible(true);
		cp.setRangeGridlinePaint(Color.gray);
		cp.setRangeGridlinesVisible(true);
		cp.setNoDataMessage("没有数据");
		
		//x轴设置
		CategoryAxis domainAxis = cp.getDomainAxis();   
        domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(100);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

		// 数据轴属性部分
		NumberAxis rangeAxis = (NumberAxis) cp.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true); // 自动生成
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		rangeAxis.setAutoRange(false);
		
		// 数据渲染部分 主要是对折线做操作
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
				
		renderer.setBaseItemLabelsVisible(false);// 设置曲线是否显示数据点
		// 设置曲线显示各数据点的值
		renderer.setSeriesPaint(0, Color.blue); // 设置折线的颜色
		 
		renderer.setBaseShapesFilled(true);
		 
		renderer.setBaseItemLabelsVisible(true);
		 
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		 
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		 
		// 解决中文乱码问题
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		//CategoryAxis domainAxis = plot.getDomainAxis();
		/*------设置X轴坐标上的文字-----------*/
		domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 11));
		/*------设置X轴的标题文字------------*/
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		/*------设置Y轴坐标上的文字-----------*/
		numberaxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 12));
		/*------设置Y轴的标题文字------------*/
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

		return chart;
	}
    
    public static JFreeChart createChart1(String chartName,DefaultXYDataset  xydataset) throws Exception {  

    	//创建主题样式  
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");  
        //设置标题字体  
        mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 12));  
        //设置轴向字体  
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 10));  
        //设置图例字体  
        mChartTheme.setRegularFont(new Font("黑体", Font.BOLD, 10));  
        //应用主题样式  
        ChartFactory.setChartTheme(mChartTheme); 

        JFreeChart chart = ChartFactory.createScatterPlot(chartName, "温度", "高程",xydataset, PlotOrientation.VERTICAL,true, false,false); 
        
        
        XYPlot xyplot = (XYPlot) chart.getPlot();    
        xyplot.setDomainGridlineStroke(new BasicStroke());
        xyplot.setRangeGridlineStroke(new BasicStroke());
        
        
        
        ValueAxis vaaxis = xyplot.getDomainAxis();    
        vaaxis.setAxisLineStroke(new BasicStroke(1.5f));
        ValueAxis va = xyplot.getDomainAxis(0);    
        va.setAxisLineStroke(new BasicStroke(0.5f));    

//        va.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细    
//        va.setAxisLinePaint(new Color(215, 215, 215)); // 坐标轴颜色    
//        xyplot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细    
//        va.setLabelPaint(new Color(10, 10, 10)); // 坐标轴标题颜色    
//        va.setTickLabelPaint(new Color(102, 102, 102)); // 坐标轴标尺值颜色    
//        ValueAxis axis = xyplot.getRangeAxis();    
//        axis.setAxisLineStroke(new BasicStroke(1.5f));    

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot    
                .getRenderer();  
        xyplot.setRenderer(xylineandshaperenderer);
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.WHITE);    
        xylineandshaperenderer.setUseOutlinePaint(true);
        xylineandshaperenderer.setSeriesStroke(0, new BasicStroke(1.0F));
        xylineandshaperenderer.setSeriesPaint(0, Color.BLUE);//设置第一条曲线颜色
        xylineandshaperenderer.setSeriesShape(0,new Ellipse2D.Double(-2, -2, 4, 4));//设置第一条曲线数据点的图形
        xylineandshaperenderer.setSeriesOutlinePaint(0,Color.black);//设置第一条曲线数据点画图型的颜色
        xylineandshaperenderer.setSeriesFillPaint(0,Color.BLUE);//设置第一条曲线数据点填充色
        xylineandshaperenderer.setSeriesShapesVisible(0,true);//第一条线数据点可见
        xylineandshaperenderer.setUseOutlinePaint(false);//设置是否画曲线数据点的轮廓图形
        xylineandshaperenderer.setUseFillPaint(true);    //设置是否填充曲线数据点 
        xylineandshaperenderer.setBaseLinesVisible(true);
        NumberAxis numberaxis = (NumberAxis) xyplot.getDomainAxis();    
        numberaxis.setAutoRangeIncludesZero(false);    
        numberaxis.setTickMarkInsideLength(2.0F);    
        numberaxis.setTickMarkOutsideLength(0.0F);    
        numberaxis.setAxisLineStroke(new BasicStroke(1.5f)); 
        return chart;
    } 

    
   
}
