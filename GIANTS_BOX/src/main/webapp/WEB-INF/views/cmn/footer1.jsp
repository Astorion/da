<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


    <!-- Start: Footer Dark -->
    <footer class="page-footer">
        <div class="container">
            
            
            
            <c:choose>
            	<c:when test="${not empty user}">
            		<div class="row">
						<div class="col-sm-3">
							<h5>Get started</h5>
							<ul>
								<li><a href="${context}/home.do">Home</a></li>
								<li><a href="${context}/logout.do">Log out</a></li>
							</ul>
						</div>
						<div class="col-sm-3">
							<h5>
								<strong>Event</strong><br>
							</h5>
							<ul>
								<li><a href="${context}/event_list.do">Explore</a></li>
								<li><a href="${context}/event_reg.do">Make Events</a></li>
								<li><a href="${context}/my_event.do">My Events</a></li>
							</ul>
						</div>
						<div class="col-sm-3">
							<h5>Support</h5>
							<ul>
								<li><a href="#">FAQ</a></li>
								<li><a href="#">Help</a></li>
								<li><a href="#">Contact us</a></li>
							</ul>
						</div>
						<div
							class="col-sm-3 d-flex justify-content-center justify-content-xl-start align-items-xl-center">
							<!-- Start: Social Icons -->
							<div class="clean-block add-on social-icons"
								style="padding: 0; padding-top: 0;">
								<div class="icons">
									<a href="#"><i class="fa fa-facebook"></i></a><a href="#"><i
										class="fa fa-instagram"></i></a><a href="#"><i
										class="fa fa-twitter"></i></a>
								</div>
							</div>
							<!-- End: Social Icons -->
						</div>
					</div>
            	</c:when>
	            <c:otherwise>
		            <div class="row">
		                <div class="col-sm-3">
		                    <h5>Get started</h5>
		                    <ul>
		                        <li><a href="${context}/main.do">Home</a></li>
		                        <li><a href="${context}/signup.do">Sign up</a></li>
		                        <li><a href="${context}/login.do">Log in</a></li>
		                    </ul>
		                </div>
		                <div class="col-sm-3">
		                    <h5><strong>Event</strong><br></h5>
		                    <ul>
		                        <li><a href="${context}/event_list.do">Explore</a></li>
		                    </ul>
		                </div>
		                <div class="col-sm-3">
		                    <h5>Support</h5>
		                    <ul>
		                        <li><a href="#">FAQ</a></li>
		                        <li><a href="#">Help</a></li>
		                        <li><a href="#">Contact us</a></li>
		                    </ul>
		                </div>
		                <div class="col-sm-3 d-flex justify-content-center justify-content-xl-start align-items-xl-center">
		                    <!-- Start: Social Icons -->
		                    <div class="clean-block add-on social-icons" style="padding: 0;padding-top: 0;">
		                        <div class="icons"><a href="#"><i class="fa fa-facebook"></i></a><a href="#"><i class="fa fa-instagram"></i></a><a href="#"><i class="fa fa-twitter"></i></a></div>
		                    </div>
		                    <!-- End: Social Icons -->
		                </div>
		            </div>
	            </c:otherwise>
            </c:choose>
            
        </div>
        <div class="footer-copyright">
            <p>© 2020 Copyright Team Uver All rights reserved</p>
        </div>
    </footer>

    
    <!-- End: Footer Dark -->
    <script src="${context}/resources/js/jquery.min.js"></script>
    <script src="${context}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="${context}/resources/js/script.min.js"></script>
    
    <!-- calendar -->	
	<script src="${context}/resources/js/datepicker.min.js"></script>
    <script src="${context}/resources/js/datepicker.en.js"></script>
    <!-- //calendar -->