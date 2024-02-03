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
            <h2
              id="bdswebApp.post.home.createOrEditLabel"
              data-cy="PostCreateUpdateHeading"
              v-text="$t(post.id ? 'bdswebApp.post.home.createOrEditLabel' : 'bdswebApp.post.home.createLabel')"
            >
              Create or edit a Post
            </h2>
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
                  <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
                    <div>
                      <div class="form-group" v-if="post.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id" v-model="post.id" readonly />
                      </div>
                      <div class="form-group">
                        <label required class="form-control-label" v-text="$t('bdswebApp.post.title')" for="post-title">Title</label>
                        <input
                          type="text"
                          class="form-control"
                          name="title"
                          id="post-title"
                          data-cy="title"
                          :class="{ valid: !$v.post.title.$invalid, invalid: $v.post.title.$invalid }"
                          v-model="$v.post.title.$model"
                          required
                        />
                        <div v-if="$v.post.title.$anyDirty && $v.post.title.$invalid">
                          <small class="form-text text-danger" v-if="!$v.post.title.required" v-text="$t('entity.validation.required')">
                            This field is required.
                          </small>
                          <small
                            class="form-text text-danger"
                            v-if="!$v.post.title.maxLength"
                            v-text="$t('entity.validation.maxlength', { max: 200 })"
                          >
                            This field cannot be longer than 200 characters.
                          </small>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.content')" for="post-content">Content</label>
                        <textarea
                          class="form-control"
                          name="content"
                          id="post-content"
                          data-cy="content"
                          :class="{ valid: !$v.post.content.$invalid, invalid: $v.post.content.$invalid }"
                          v-model="$v.post.content.$model"
                        ></textarea>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.price')" for="post-price">Price</label>
                        <input
                          type="number"
                          class="form-control"
                          name="price"
                          id="post-price"
                          data-cy="price"
                          :class="{ valid: !$v.post.price.$invalid, invalid: $v.post.price.$invalid }"
                          v-model.number="$v.post.price.$model"
                          required
                        />
                        <div v-if="$v.post.price.$anyDirty && $v.post.price.$invalid">
                          <small class="form-text text-danger" v-if="!$v.post.price.required" v-text="$t('entity.validation.required')">
                            This field is required.
                          </small>
                          <small class="form-text text-danger" v-if="!$v.post.price.numeric" v-text="$t('entity.validation.number')">
                            This field should be a number.
                          </small>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.square')" for="post-square">Square</label>
                        <input
                          type="number"
                          class="form-control"
                          name="square"
                          id="post-square"
                          data-cy="square"
                          :class="{ valid: !$v.post.square.$invalid, invalid: $v.post.square.$invalid }"
                          v-model.number="$v.post.square.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.address')" for="post-address">Address</label>
                        <input
                          type="text"
                          class="form-control"
                          name="address"
                          id="post-address"
                          data-cy="address"
                          :class="{ valid: !$v.post.address.$invalid, invalid: $v.post.address.$invalid }"
                          v-model="$v.post.address.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.googleMapsLocation')" for="post-googleMapsLocation"
                          >Google Maps Location</label
                        >
                        <input
                          type="text"
                          class="form-control"
                          name="googleMapsLocation"
                          id="post-googleMapsLocation"
                          data-cy="googleMapsLocation"
                          :class="{ valid: !$v.post.googleMapsLocation.$invalid, invalid: $v.post.googleMapsLocation.$invalid }"
                          v-model="$v.post.googleMapsLocation.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.width')" for="post-width">Width</label>
                        <input
                          type="number"
                          class="form-control"
                          name="width"
                          id="post-width"
                          data-cy="width"
                          :class="{ valid: !$v.post.width.$invalid, invalid: $v.post.width.$invalid }"
                          v-model.number="$v.post.width.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.length')" for="post-length">Length</label>
                        <input
                          type="number"
                          class="form-control"
                          name="length"
                          id="post-length"
                          data-cy="length"
                          :class="{ valid: !$v.post.length.$invalid, invalid: $v.post.length.$invalid }"
                          v-model.number="$v.post.length.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.direction')" for="post-direction">Direction</label>
                        <input
                          type="text"
                          class="form-control"
                          name="direction"
                          id="post-direction"
                          data-cy="direction"
                          :class="{ valid: !$v.post.direction.$invalid, invalid: $v.post.direction.$invalid }"
                          v-model="$v.post.direction.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.distance')" for="post-distance">Distance</label>
                        <input
                          type="text"
                          class="form-control"
                          name="distance"
                          id="post-distance"
                          data-cy="distance"
                          :class="{ valid: !$v.post.distance.$invalid, invalid: $v.post.distance.$invalid }"
                          v-model="$v.post.distance.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.legal')" for="post-legal">Legal</label>
                        <input
                          type="text"
                          class="form-control"
                          name="legal"
                          id="post-legal"
                          data-cy="legal"
                          :class="{ valid: !$v.post.legal.$invalid, invalid: $v.post.legal.$invalid }"
                          v-model="$v.post.legal.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.numberOfFloors')" for="post-numberOfFloors"
                          >Number Of Floors</label
                        >
                        <input
                          type="number"
                          class="form-control"
                          name="numberOfFloors"
                          id="post-numberOfFloors"
                          data-cy="numberOfFloors"
                          :class="{ valid: !$v.post.numberOfFloors.$invalid, invalid: $v.post.numberOfFloors.$invalid }"
                          v-model.number="$v.post.numberOfFloors.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.numberOfBedroom')" for="post-numberOfBedroom"
                          >Number Of Bedroom</label
                        >
                        <input
                          type="number"
                          class="form-control"
                          name="numberOfBedroom"
                          id="post-numberOfBedroom"
                          data-cy="numberOfBedroom"
                          :class="{ valid: !$v.post.numberOfBedroom.$invalid, invalid: $v.post.numberOfBedroom.$invalid }"
                          v-model.number="$v.post.numberOfBedroom.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.hasKitchen')" for="post-hasKitchen">Has Kitchen</label>
                        <input
                          type="checkbox"
                          class="form-check"
                          name="hasKitchen"
                          id="post-hasKitchen"
                          data-cy="hasKitchen"
                          :class="{ valid: !$v.post.hasKitchen.$invalid, invalid: $v.post.hasKitchen.$invalid }"
                          v-model="$v.post.hasKitchen.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.hasDinningRoom')" for="post-hasDinningRoom"
                          >Has Dinning Room</label
                        >
                        <input
                          type="checkbox"
                          class="form-check"
                          name="hasDinningRoom"
                          id="post-hasDinningRoom"
                          data-cy="hasDinningRoom"
                          :class="{ valid: !$v.post.hasDinningRoom.$invalid, invalid: $v.post.hasDinningRoom.$invalid }"
                          v-model="$v.post.hasDinningRoom.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.hasRooftop')" for="post-hasRooftop">Has Rooftop</label>
                        <input
                          type="checkbox"
                          class="form-check"
                          name="hasRooftop"
                          id="post-hasRooftop"
                          data-cy="hasRooftop"
                          :class="{ valid: !$v.post.hasRooftop.$invalid, invalid: $v.post.hasRooftop.$invalid }"
                          v-model="$v.post.hasRooftop.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.hasGarage')" for="post-hasGarage">Has Garage</label>
                        <input
                          type="checkbox"
                          class="form-check"
                          name="hasGarage"
                          id="post-hasGarage"
                          data-cy="hasGarage"
                          :class="{ valid: !$v.post.hasGarage.$invalid, invalid: $v.post.hasGarage.$invalid }"
                          v-model="$v.post.hasGarage.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.isVip')" for="post-isVip">Is Vip</label>
                        <input
                          type="checkbox"
                          class="form-check"
                          name="isVip"
                          id="post-isVip"
                          data-cy="isVip"
                          :class="{ valid: !$v.post.isVip.$invalid, invalid: $v.post.isVip.$invalid }"
                          v-model="$v.post.isVip.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.postingTime')" for="post-postingTime"
                          >Posting Time</label
                        >
                        <b-input-group class="mb-3">
                          <b-input-group-prepend>
                            <b-form-datepicker
                              aria-controls="post-postingTime"
                              v-model="$v.post.postingTime.$model"
                              name="postingTime"
                              class="form-control"
                              :locale="currentLanguage"
                              button-only
                              today-button
                              reset-button
                              close-button
                            >
                            </b-form-datepicker>
                          </b-input-group-prepend>
                          <b-form-input
                            id="post-postingTime"
                            data-cy="postingTime"
                            type="text"
                            class="form-control"
                            name="postingTime"
                            :class="{ valid: !$v.post.postingTime.$invalid, invalid: $v.post.postingTime.$invalid }"
                            v-model="$v.post.postingTime.$model"
                          />
                        </b-input-group>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.expiredTime')" for="post-expiredTime"
                          >Expired Time</label
                        >
                        <b-input-group class="mb-3">
                          <b-input-group-prepend>
                            <b-form-datepicker
                              aria-controls="post-expiredTime"
                              v-model="$v.post.expiredTime.$model"
                              name="expiredTime"
                              class="form-control"
                              :locale="currentLanguage"
                              button-only
                              today-button
                              reset-button
                              close-button
                            >
                            </b-form-datepicker>
                          </b-input-group-prepend>
                          <b-form-input
                            id="post-expiredTime"
                            data-cy="expiredTime"
                            type="text"
                            class="form-control"
                            name="expiredTime"
                            :class="{ valid: !$v.post.expiredTime.$invalid, invalid: $v.post.expiredTime.$invalid }"
                            v-model="$v.post.expiredTime.$model"
                          />
                        </b-input-group>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.brokerageFees')" for="post-brokerageFees"
                          >Brokerage Fees</label
                        >
                        <input
                          type="number"
                          class="form-control"
                          name="brokerageFees"
                          id="post-brokerageFees"
                          data-cy="brokerageFees"
                          :class="{ valid: !$v.post.brokerageFees.$invalid, invalid: $v.post.brokerageFees.$invalid }"
                          v-model.number="$v.post.brokerageFees.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.status')" for="post-status">Status</label>
                        <select
                          class="form-control"
                          name="status"
                          :class="{ valid: !$v.post.status.$invalid, invalid: $v.post.status.$invalid }"
                          v-model="$v.post.status.$model"
                          id="post-status"
                          data-cy="status"
                        >
                          <option
                            v-for="postStatus in postStatusValues"
                            :key="postStatus"
                            v-bind:value="postStatus"
                            v-bind:label="$t('bdswebApp.PostStatus.' + postStatus)"
                          >
                            {{ postStatus }}
                          </option>
                        </select>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.star')" for="post-star">Star</label>
                        <input
                          type="number"
                          class="form-control"
                          name="star"
                          id="post-star"
                          data-cy="star"
                          :class="{ valid: !$v.post.star.$invalid, invalid: $v.post.star.$invalid }"
                          v-model.number="$v.post.star.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.hash')" for="post-hash">Hash</label>
                        <input
                          type="text"
                          class="form-control"
                          name="hash"
                          id="post-hash"
                          data-cy="hash"
                          :class="{ valid: !$v.post.hash.$invalid, invalid: $v.post.hash.$invalid }"
                          v-model="$v.post.hash.$model"
                        />
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.type')" for="post-type">Type</label>
                        <select class="form-control" id="post-type" data-cy="type" name="type" v-model="post.type" required>
                          <option v-if="!post.type" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.type && postTypeOption.id === post.type.id ? post.type : postTypeOption"
                            v-for="postTypeOption in postTypes"
                            :key="postTypeOption.id"
                          >
                            {{ postTypeOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.type.$anyDirty && $v.post.type.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.type.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.category')" for="post-category">Category</label>
                        <select class="form-control" id="post-category" data-cy="category" name="category" v-model="post.category" required>
                          <option v-if="!post.category" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.category && categoryOption.id === post.category.id ? post.category : categoryOption"
                            v-for="categoryOption in categories"
                            :key="categoryOption.id"
                          >
                            {{ categoryOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.category.$anyDirty && $v.post.category.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.category.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.user')" for="post-user">User</label>
                        <select class="form-control" id="post-user" data-cy="user" name="user" v-model="post.user" required>
                          <option v-if="!post.user" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.user && userOption.id === post.user.id ? post.user : userOption"
                            v-for="userOption in users"
                            :key="userOption.id"
                          >
                            {{ userOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.user.$anyDirty && $v.post.user.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.user.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.province')" for="post-province">Province</label>
                        <select class="form-control" id="post-province" data-cy="province" name="province" v-model="post.province" required>
                          <option v-if="!post.province" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.province && provinceOption.id === post.province.id ? post.province : provinceOption"
                            v-for="provinceOption in provinces"
                            :key="provinceOption.id"
                          >
                            {{ provinceOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.province.$anyDirty && $v.post.province.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.province.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.district')" for="post-district">District</label>
                        <select class="form-control" id="post-district" data-cy="district" name="district" v-model="post.district" required>
                          <option v-if="!post.district" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.district && districtOption.id === post.district.id ? post.district : districtOption"
                            v-for="districtOption in districts"
                            :key="districtOption.id"
                          >
                            {{ districtOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.district.$anyDirty && $v.post.district.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.district.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.ward')" for="post-ward">Ward</label>
                        <select class="form-control" id="post-ward" data-cy="ward" name="ward" v-model="post.ward">
                          <option v-bind:value="null"></option>
                          <option
                            v-bind:value="post.ward && wardOption.id === post.ward.id ? post.ward : wardOption"
                            v-for="wardOption in wards"
                            :key="wardOption.id"
                          >
                            {{ wardOption.id }}
                          </option>
                        </select>
                      </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('bdswebApp.post.street')" for="post-street">Street</label>
                        <select class="form-control" id="post-street" data-cy="street" name="street" v-model="post.street" required>
                          <option v-if="!post.street" v-bind:value="null" selected></option>
                          <option
                            v-bind:value="post.street && streetOption.id === post.street.id ? post.street : streetOption"
                            v-for="streetOption in streets"
                            :key="streetOption.id"
                          >
                            {{ streetOption.id }}
                          </option>
                        </select>
                      </div>
                      <div v-if="$v.post.street.$anyDirty && $v.post.street.$invalid">
                        <small class="form-text text-danger" v-if="!$v.post.street.required" v-text="$t('entity.validation.required')">
                          This field is required.
                        </small>
                      </div>
                    </div>
                    <div>
                      <button
                        type="button"
                        id="cancel-save"
                        data-cy="entityCreateCancelButton"
                        class="btn btn-secondary"
                        v-on:click="previousState()"
                      >
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                      </button>
                      <button
                        type="submit"
                        id="save-entity"
                        data-cy="entityCreateSaveButton"
                        :disabled="$v.post.$invalid || isSaving"
                        class="btn btn-primary"
                      >
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                      </button>
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

<script lang="ts" src="./new-post.component.ts"></script>
