<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>Keep in touch with us</h6>
              <h2>Feel free to send us a message about your business needs</h2>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="contact-page">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="inner-content">
              <div class="row">
                <div class="col-lg-6">
                  <div id="map">
                    <iframe
                      src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d59588.138515313396!2d105.81172805498974!3d21.022334053699108!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab9bd9861ca1%3A0xe7887f7b72ca17a9!2zSMOgIE7hu5lpLCBWaeG7h3QgTmFt!5e0!3m2!1svi!2s!4v1705824413165!5m2!1svi!2s"
                      width="100%"
                      height="650px"
                      style="border: 0"
                      allowfullscreen=""
                      loading="lazy"
                      referrerpolicy="no-referrer-when-downgrade"
                    ></iframe>
                    <!--                    <iframe src="https://maps.google.com/maps?q=Av.+L%C3%BAcio+Costa,+Rio+de+Janeiro+-+RJ,+Brazil&t=&z=13&ie=UTF8&iwloc=&output=embed" width="100%" height="650px" frameborder="0" style="border:0" allowfullscreen></iframe>-->
                  </div>
                </div>
                <div class="col-lg-6 align-self-center">
                  <form id="contact" action="" method="get">
                    <div class="row">
                      <div class="col-lg-6">
                        <fieldset>
                          <input type="name" name="name" id="name" placeholder="Name" autocomplete="on" required />
                        </fieldset>
                      </div>
                      <div class="col-lg-6">
                        <fieldset>
                          <input type="surname" name="surname" id="surname" placeholder="Surname" autocomplete="on" required />
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <input type="text" name="email" id="email" pattern="[^ @]*@[^ @]*" placeholder="Your Email" required="" />
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <ul>
                          <li><input type="checkbox" name="option1" value="cars" /><span>Cars</span></li>
                          <li><input type="checkbox" name="option2" value="aparmtents" /><span>Apartments</span></li>
                          <li><input type="checkbox" name="option3" value="shopping" /><span>Shopping</span></li>
                          <li><input type="checkbox" name="option4" value="food" /><span>Food &amp; Life</span></li>
                          <li><input type="checkbox" name="option5" value="traveling" /><span>Traveling</span></li>
                        </ul>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <textarea
                            name="message"
                            type="text"
                            class="form-control"
                            id="message"
                            placeholder="Message"
                            required=""
                          ></textarea>
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <button type="submit" id="form-submit" class="main-button"><i class="fa fa-paper-plane"></i> Send Message</button>
                        </fieldset>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<!--<script lang="ts" src="./home.component.ts"></script>-->
<!-- Scripts -->
<script>
import jQuery from '../../vendor/jquery/jquery.min.js';
import PostComponent from '@/component/post/post.vue';
import 'owl.carousel';
export default {
  components: {
    PostComponent,
  },
  data() {
    return {};
  },
  created() {
    jQuery(document).ready(function ($) {
      // Page loading animation
      $(window).on('load', function () {
        $('#js-preloader').addClass('loaded');
      });
    });
  },
  watch: {},
};
</script>
