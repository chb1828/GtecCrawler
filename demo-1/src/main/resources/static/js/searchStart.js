$(document).ready(function() 
			{
				// 조건 숨기기
				$(".naverLayer").hide();
				$(".googleLayer").hide();
				$(".kakaoLayer").hide();
				
				// 검색 조건 레이어 구분
				$("#selectSearchEngine").change(function() 
				{
					var selected = $("#selectSearchEngine option:selected").val();
					
					if ( selected == "naver" ) 
					{
						$(".naverLayer").show();
					} else 
					{
						$(".naverLayer").hide();
					}
					
					if ( selected == "kakao" ) 
					{
						$(".kakaoLayer").show();
					} else 
					{
						$(".kakaoLayer").hide();
					}
				});
				
				// 페이지 이동 함수
				function change_location()
				{
					var searchEngine = $("#selectSearchEngine option:selected").val();
					
					if(searchEngine == "naver")
					{
						location.href='/visualization';
					}
					
					if(searchEngine == "kakao")
					{
						location.href='/visualization';
					}
				}
				
				// reset 버튼 클릭
				$("#resetButton").click(function()
				{
					var resetModal = document.getElementById('resetModal');
					var btn = document.getElementById("resetButton");
					var span = document.getElementsByClassName("close")[0];  
					
					resetModal.style.display = "block";

					window.onclick = function(event) {
					    if (event.target == resetModal) {
					    	resetModal.style.display = "none";
					    };
					};
				});
				
				// searchStart 버튼 클릭
				$("#startCrawling").click(function()
				{
					// selectSearchEngine id로 값 가져오기
					var searchEngine = $("#selectSearchEngine option:selected").val();

					// 네이버 검색 조건
					var nCheckBox = $('input[name=naverCB]:checked').val();
					// 카카오 검색 조건
		       	 	var kCheckBox = $('input[name=kakaoCB]:checked').val();
					
					// 검색 시작한 시간
					var date = new Date();
		        	var currentDate = date.getFullYear() + "년 " + ( date.getMonth() + 1 ) + "월 " + date.getDate() + "일 ";
		       	 	var currentTime = date.getHours() + "시 " + date.getMinutes() + "분 ";
					var time = currentDate + currentTime;
					
		       	 	// 검색어
					var searchWord = $('#name').val();
					
		       	 	// 넘길 값
					var nData = { "searchEngine":searchEngine, "time":time, "searchWord":searchWord, "checkBoxes":nCheckBox };
					var kData = { "searchEngine":searchEngine, "time":time, "searchWord":searchWord, "checkBoxes":kCheckBox };
					
					// 모달창
					var searchStartModal = document.getElementById('searchStartModal');
					var searchStartEngineModal = document.getElementById('searchStartEngineModal');
					var searchStartConditionModal = document.getElementById('searchStartConditionModal');
					var searchStartWordModal = document.getElementById('searchStartWordModal');
					var btn = document.getElementById("startCrawling");
					var span = document.getElementsByClassName("close")[0];  
					
					if(searchEngine == "")
					{
						searchStartEngineModal.style.display = "block";

						window.onclick = function(event) {
						    if (event.target == searchStartEngineModal) {
						    	searchStartEngineModal.style.display = "none";
						    };
						};
					}
					else
					{
						// 검색엔진이 네이버일때
						if(searchEngine == "naver")
						{
							// 네이버 검색 조건
							if(nCheckBox == null)
							{
								searchStartConditionModal.style.display = "block";
								
								window.onclick = function(event) {
								    if (event.target == searchStartConditionModal) {
								    	searchStartConditionModal.style.display = "none";
								    };
								};
							}
							// 검색어
							else if(searchWord == "")
							{
								searchStartWordModal.style.display = "block";

								window.onclick = function(event) {
								    if (event.target == searchStartWordModal) {
								    	searchStartWordModal.style.display = "none";
								    };
								};
							}
							else
							{
								searchStartModal.style.display = "block";

								window.onclick = function(event) {
								    if (event.target == searchStartModal) {
								    	searchStartModal.style.display = "none";
								    };
								};

								$.ajax({
									url : "/searchingStart.n",
									type : "POST",
									data : nData,
									success : function(data)
									{
										location.href="/visualization";
									},
									error : function(request, status, error) 
									{
										alert("실패");
									}
								});
							}
						}
						
						// 검색엔진이 카카오일때
						if(searchEngine == "kakao")
						{
							// 카카오 검색 조건
							if(kCheckBox == null)
							{
								searchStartConditionModal.style.display = "block";

								window.onclick = function(event) {
								    if (event.target == searchStartConditionModal) {
								    	searchStartConditionModal.style.display = "none";
								    };
								};
							}
							// 검색어
							else if(searchWord == "")
							{
								searchStartWordModal.style.display = "block";

								window.onclick = function(event) {
								    if (event.target == searchStartWordModal) {
								    	searchStartWordModal.style.display = "none";
								    };
								};
							}
							else
							{
								searchStartModal.style.display = "block";

								window.onclick = function(event) {
								    if (event.target == searchStartModal) {
								    	searchStartModal.style.display = "none";
								    };
								};
								
								$.ajax({
									url: "/searchingStart.k",
									type: "POST",
									data: kData,
									success : function(data)
									{
										location.href="/visualization";
									},
									error : function(request, status, error) 
									{
										alert("실패");
									}
								});
							}
						}
					}
					
					/*// 검색 조건 확인
					if(searchEngine == "")
					{
						alert("검색엔진을 선택해주세요.");
					}
					else if(nCheckBox == null)
					{
						alert("검색 조건을 체크해주세요.");
					}
					else if(kCheckBox == null)
					{
						alert("검색 조건을 체크해주세요.");
					}
					else if(searchWord == "")
					{
						alert("검색어를 입력해주세요.");
					}
					else
					{
						btn.onclick = function() {
						    modal.style.display = "block";
						};

						span.onclick = function() {
						    modal.style.display = "none";
						};

						window.onclick = function(event) {
						    if (event.target == modal) {
						        modal.style.display = "none";
						    };
						};
						
						if(searchEngine == "naver")
						{
							$.ajax({
								url : "/searchingStart.n",
								type : "POST",
								data : nData,
								success : function(data)
								{
									alert("성공");
								},
								error : function(request, status, error) 
								{
									alert("실패");
								}
							});
						}
						
						if(searchEngine == "kakao") 
						{
							$.ajax({
								url: "/searchingStart.k",
								type: "POST",
								data: kData,
								success : function(data)
								{
									alert("성공");
								},
								error : function(request, status, error) 
								{
									alert("실패");
								}
							});
						}
					}*/
					
				});
				$("#name").keydown(function(key) {
					if(key.keyCode ==13) {
						var searchEngine = $("#selectSearchEngine option:selected").val();

						// 네이버 검색 조건
						var nCheckBox = $('input[name=naverCB]:checked').val();
						// 카카오 검색 조건
			       	 	var kCheckBox = $('input[name=kakaoCB]:checked').val();
						
						// 검색 시작한 시간
						var date = new Date();
			        	var currentDate = date.getFullYear() + "년 " + ( date.getMonth() + 1 ) + "월 " + date.getDate() + "일 ";
			       	 	var currentTime = date.getHours() + "시 " + date.getMinutes() + "분 ";
						var time = currentDate + currentTime;
						
			       	 	// 검색어
						var searchWord = $('#name').val();
						
			       	 	// 넘길 값
						var nData = { "searchEngine":searchEngine, "time":time, "searchWord":searchWord, "checkBoxes":nCheckBox };
						var kData = { "searchEngine":searchEngine, "time":time, "searchWord":searchWord, "checkBoxes":kCheckBox };
						
						// 모달창
						var searchStartModal = document.getElementById('searchStartModal');
						var searchStartEngineModal = document.getElementById('searchStartEngineModal');
						var searchStartConditionModal = document.getElementById('searchStartConditionModal');
						var searchStartWordModal = document.getElementById('searchStartWordModal');
						var btn = document.getElementById("startCrawling");
						var span = document.getElementsByClassName("close")[0];  
						
						if(searchEngine == "")
						{
							searchStartEngineModal.style.display = "block";

							window.onclick = function(event) {
							    if (event.target == searchStartEngineModal) {
							    	searchStartEngineModal.style.display = "none";
							    };
							};
						}
						else
						{
							// 검색엔진이 네이버일때
							if(searchEngine == "naver")
							{
								// 네이버 검색 조건
								if(nCheckBox == null)
								{
									searchStartConditionModal.style.display = "block";
									
									window.onclick = function(event) {
									    if (event.target == searchStartConditionModal) {
									    	searchStartConditionModal.style.display = "none";
									    };
									};
								}
								// 검색어
								else if(searchWord == "")
								{
									searchStartWordModal.style.display = "block";

									window.onclick = function(event) {
									    if (event.target == searchStartWordModal) {
									    	searchStartWordModal.style.display = "none";
									    };
									};
								}
								else
								{
									searchStartModal.style.display = "block";

									window.onclick = function(event) {
									    if (event.target == searchStartModal) {
									    	searchStartModal.style.display = "none";
									    };
									};

									$.ajax({
										url : "/searchingStart.n",
										type : "POST",
										data : nData,
										success : function(data)
										{
											location.href="/visualization";
										},
										error : function(request, status, error) 
										{
											alert("실패");
										}
									});
								}
							}
							
							// 검색엔진이 카카오일때
							if(searchEngine == "kakao")
							{
								// 카카오 검색 조건
								if(kCheckBox == null)
								{
									searchStartConditionModal.style.display = "block";

									window.onclick = function(event) {
									    if (event.target == searchStartConditionModal) {
									    	searchStartConditionModal.style.display = "none";
									    };
									};
								}
								// 검색어
								else if(searchWord == "")
								{
									searchStartWordModal.style.display = "block";

									window.onclick = function(event) {
									    if (event.target == searchStartWordModal) {
									    	searchStartWordModal.style.display = "none";
									    };
									};
								}
								else
								{
									searchStartModal.style.display = "block";

									window.onclick = function(event) {
									    if (event.target == searchStartModal) {
									    	searchStartModal.style.display = "none";
									    };
									};
									
									$.ajax({
										url: "/searchingStart.k",
										type: "POST",
										data: kData,
										success : function(data)
										{
											location.href="/visualization";
										},
										error : function(request, status, error) 
										{
											alert("실패");
										}
									});
								}
							}
						}
					}
				});
				
				function goStartCrawling() {
					
				}
			});