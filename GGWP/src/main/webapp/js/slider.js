window.onload=function(){ 
    const evidSlider = document.querySelector("#evidSlider");
	const recSlider = document.querySelector("#recSlider");
    const nextBtn1 = document.querySelector("#next-btn1");
    const nextBtn2 = document.querySelector("#next-btn2");
    const prevBtn1 = document.querySelector("#prev-btn1");
    const prevBtn2 = document.querySelector("#prev-btn2");
    const slides = document.querySelectorAll(".slide");
	const slides2 = document.querySelectorAll(".slide2");
    const slideIcons = document.querySelectorAll(".slide-icon");
	const slideIcons2 = document.querySelectorAll(".slide-icon2");
    const numberOfSlides = slides.length;
	const numberOfSlides2 = slides2.length;
    var slideNumber = 0,slideNumber2=0;

	//"IN EVIDENZA"
    //image slider next button	slider
    nextBtn1.addEventListener("click", () => {
      slides.forEach((slide) => {
        slide.classList.remove("active");
      });
      slideIcons.forEach((slideIcon) => {
        slideIcon.classList.remove("active");
      });

      slideNumber++;

      if(slideNumber > (numberOfSlides - 1)){
        slideNumber = 0;
      }

      slides[slideNumber].classList.add("active");
    });

    //image slider previous button
    prevBtn1.addEventListener("click", () => {
      slides.forEach((slide) => {
        slide.classList.remove("active");
      });
      slideIcons.forEach((slideIcon) => {
        slideIcon.classList.remove("active");
      });

      slideNumber--;

      if(slideNumber < 0){
        slideNumber = numberOfSlides - 1;
      }

      slides[slideNumber].classList.add("active");
    });

    //image slider autoplay
    
    repeater = () => {
      playSlider = setInterval(function(){
        slides.forEach((slide) => {
          slide.classList.remove("active");
        });
        slideIcons.forEach((slideIcon) => {
          slideIcon.classList.remove("active");
        });

        slideNumber++;

        if(slideNumber > (numberOfSlides - 1)){
          slideNumber = 0;
        }

        slides[slideNumber].classList.add("active");
      }, 7000);
    }
    repeater();

    //stop the image slider autoplay on mouseover
    evidSlider.addEventListener("mouseover", () => {
      clearInterval(playSlider);
    });

    //start the image slider autoplay again on mouseout
    evidSlider.addEventListener("mouseout", () => {
      repeater();
    });

	//"RECENTI"
    //image slider next button	slider
    nextBtn2.addEventListener("click", () => {
      slides2.forEach((slide2) => {
        slide2.classList.remove("active");
      });
      slideIcons2.forEach((slideIcon2) => {
        slideIcon2.classList.remove("active");
      });

      slideNumber2++;

      if(slideNumber2 > (numberOfSlides2 - 1)){
        slideNumber2 = 0;
      }

      slides2[slideNumber2].classList.add("active");
    });

    //image slider previous button
    prevBtn2.addEventListener("click", () => {
      slides2.forEach((slide2) => {
        slide2.classList.remove("active");
      });
      slideIcons2.forEach((slideIcon2) => {
        slideIcon2.classList.remove("active");
      });

      slideNumber2--;

      if(slideNumber2 < 0){
        slideNumber2 = numberOfSlides2 - 1;
      }

      slides2[slideNumber2].classList.add("active");
    });

    //image slider autoplay

    repeater = () => {
      playSlider = setInterval(function(){
        slides2.forEach((slide2) => {
          slide2.classList.remove("active");
        });
        slideIcons2.forEach((slideIcon2) => {
          slideIcon2.classList.remove("active");
        });

        slideNumber2++;

        if(slideNumber2 > (numberOfSlides2 - 1)){
          slideNumber2 = 0;
        }

        slides2[slideNumber2].classList.add("active");        
      }, 5000);
    }
    repeater();

    //stop the image slider autoplay on mouseover
    recSlider.addEventListener("mouseover", () => {
      clearInterval(playSlider);
    });

    //start the image slider autoplay again on mouseout
    recSlider.addEventListener("mouseout", () => {
      repeater();
    });
}