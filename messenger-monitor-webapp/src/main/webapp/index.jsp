<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>FIRST ontology mappings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 40px;
      }

      /* Custom container */
      .container-narrow {
        margin: 0 auto;
        max-width: 700px;
      }
      .container-narrow > hr {
        margin: 30px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 60px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 72px;
        line-height: 1;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }
      /* modifications */
      .row-fluid {
        word-wrap:break-word;
      }
      .jumbotron h2 {
         -moz-text-shadow: 1px 1px 2px #000;
         -webkit-text-shadow: 3px 3px 4px #000;
		 text-shadow: 1px 1px 2px #999;
      }
      
    </style>
<!--     <link href="css/bootstrap-responsive.css" rel="stylesheet">
 -->
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="ico/favicon.png">
  </head>

  <body>

    <div class="container-fluid">

      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
        <h3 class="muted"><img src="images/logo.png"></h3>
      </div>

      <hr>

      <div class="jumbotron">
        
<script src="js/jquery-1.9.1.min.js"></script>
            	<script src="highcharts/js/highcharts.js"></script>
            	

<div id="container3" style="height: 250px; min-width: 300px"></div>
				<script type="text/javascript">
				(function($){ // encapsulate jQuery

					
					$(function () {
						
							colors = colors = Highcharts.getOptions().colors,
							seriesCounter = 0,
							seriesOptions = [],
							yAxisOptions = [];
						
							colors [0] = 'rgba(31, 119, 180, 0.9)';
							colors [1] = 'rgba(255, 127, 14, 0.9)';
							colors [2] = 'rgba(44, 160, 44, 0.9)';
							colors [3] = 'rgba(214, 39, 40, 0.9)';

						var d = new Date();
						var curr_date = d.getDate();
						var curr_month = d.getMonth() + 1;
						var curr_year = d.getFullYear();
						
						var todayDate = curr_year + '-' + curr_month + '-' + curr_date;
						
						//$.each(names, function(i, name) {

						$.getJSON('./GetReceiveTimeSeriesAggregated',	function(data) {
							var names = new Array();
							for (n in data) {
								names.push(n);
							}
							for (var i=0; i<names.length; i++) {
								seriesOptions[i] = {
										name: names[i],
										data: data[names[i]],
										color: colors[i],
										//showInLegend: false,
										marker: {
							                	enabled: false
							            },
							            type: 'spline'
										
									};
							}
							

							createChart();
						});
						
						
						function createChart() {
						
						
					        $('#container3').highcharts({
					        	
					        	credits: {
					        		enabled: false
					        	},
					            chart: {
					                type: 'area',
					                //marginRight: 130,
					                marginBottom: 25,
					                backgroundColor: '#ffffff', 
					                
					                marginRight: 25
					            },
					            title: {
					                text: 'Processed documents',
					                x: -20, //center
					                style: {
					                    fontSize: '16px',
					                    fontFamily: 'Arial, sans-serif',
					                    fontWeight: 'bold'
					                }
					            },
					            subtitle: {
					                text: 'per hour',
					                x: -20
					            },
					            
					            xAxis: {
					            	type: 'datetime'
					            	//min: 1366908900196,
					            	//max: 1366909020337
					            },
					            
					            yAxis: {
					                title: {
					                    text: 'documents / hour'
					                },
					                plotLines: [{
					                    value: 0,
					                    width: 1,
					                    color: '#808080'
					                }],
									min: 0
					            },
					            tooltip: {
					                valueSuffix: ''
					            },
					            series: seriesOptions,
					            tooltip: {
							    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
							    	valueDecimals: 2,
							    	crosshairs: true,
							    	shared: true
							    },
							    legend: {
					                layout: 'vertical',
					                align: 'right',
					                verticalAlign: 'top',
					                x: -10,
					                y: 10,
					                borderWidth: 0,
					                style: {
					                    fontSize: '13px',
					                    fontFamily: 'Arial, sans-serif'
					                }
					            },
					        });
						}
					    });
					    

					})(jQuery);

</script>


<div id="container4" style="height: 250px; min-width: 300px"></div>
				<script type="text/javascript">
				(function($){ // encapsulate jQuery

					
					$(function () {
						
							colors = colors = Highcharts.getOptions().colors,
							seriesCounter = 0,
							seriesOptions = [],
							yAxisOptions = [];
						
						colors [0] = 'rgba(31, 119, 180, 0.9)';
						colors [1] = 'rgba(255, 127, 14, 0.9)';
						colors [2] = 'rgba(44, 160, 44, 0.9)';
						colors [3] = 'rgba(214, 39, 40, 0.9)';

						var d = new Date();
						var curr_date = d.getDate();
						var curr_month = d.getMonth() + 1;
						var curr_year = d.getFullYear();
						
						var todayDate = curr_year + '-' + curr_month + '-' + curr_date;
						
						//$.each(names, function(i, name) {

						$.getJSON('./GetLostOnReceiveTimeSeriesAggregated',	function(data) {
							var names = new Array();
							for (n in data) {
								names.push(n);
							}
							for (var i=0; i<names.length; i++) {
								seriesOptions[i] = {
										name: names[i],
										data: data[names[i]],
										color: colors[i],
										//showInLegend: false,
										marker: {
							                	enabled: false
							            },
							            type: 'spline'
										
									};
							}
							

							createChart();
						});
						
						
						function createChart() {
						
						
					        $('#container4').highcharts({
					        	
					        	credits: {
					        		enabled: false
					        	},
					            chart: {
					                type: 'area',
					                //marginRight: 130,
					                marginBottom: 25,
					                backgroundColor: '#ffffff', 
					                
					                marginRight: 25
					            },
					            title: {
					                text: 'Documents dropped on receive',
					                x: -20, //center
					                style: {
					                    fontSize: '16px',
					                    fontFamily: 'Arial, sans-serif',
					                    fontWeight: 'bold'
					                }
					            },
					            subtitle: {
					                text: 'per hour',
					                x: -20
					            },
					            
					            xAxis: {
					            	type: 'datetime'
					            	//min: 1366908900196,
					            	//max: 1366909020337
					            },
					            
					            yAxis: {
					                title: {
					                    text: 'documents / hour'
					                },
					                plotLines: [{
					                    value: 0,
					                    width: 1,
					                    color: '#808080'
					                }],
									min: 0
					            },
					            tooltip: {
					                valueSuffix: ''
					            },
					            series: seriesOptions,
					            tooltip: {
							    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
							    	valueDecimals: 2,
							    	crosshairs: true,
							    	shared: true
							    },
							    legend: {
					                layout: 'vertical',
					                align: 'right',
					                verticalAlign: 'top',
					                x: -10,
					                y: 10,
					                borderWidth: 0,
					                style: {
					                    fontSize: '13px',
					                    fontFamily: 'Arial, sans-serif'
					                }
					            },
					        });
						}
					    });
					    

					})(jQuery);

</script>

	  </div>
        

	 
      

    
      
      <hr>

      <div class="footer">
        <p>&nbsp; ATOS</p>
      </div>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.9.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/script.js"></script>
<!--     <script src="js/bootstrap-transition.js"></script>
    <script src="js/bootstrap-alert.js"></script>
    <script src="js/bootstrap-modal.js"></script>
    <script src="js/bootstrap-dropdown.js"></script>
    <script src="js/bootstrap-scrollspy.js"></script>
    <script src="js/bootstrap-tab.js"></script>
    <script src="js/bootstrap-tooltip.js"></script>
    <script src="js/bootstrap-popover.js"></script>
    <script src="js/bootstrap-button.js"></script>
    <script src="js/bootstrap-collapse.js"></script>
    <script src="js/bootstrap-carousel.js"></script>
    <script src="js/bootstrap-typeahead.js"></script>
 -->
  </body>
</html>
