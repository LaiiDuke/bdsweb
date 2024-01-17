<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="bdswebApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">Create or edit a Post</h2>
        <div>
          <div class="form-group" v-if="post.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="post.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-title">Title</label>
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
              <small class="form-text text-danger" v-if="!$v.post.title.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.post.title.maxLength">
                This field cannot be longer than 200 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-content">Content</label>
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
            <label class="form-control-label" for="post-price">Price</label>
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
              <small class="form-text text-danger" v-if="!$v.post.price.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.post.price.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-square">Square</label>
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
            <label class="form-control-label" for="post-address">Address</label>
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
            <label class="form-control-label" for="post-googleMapsLocation">Google Maps Location</label>
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
            <label class="form-control-label" for="post-width">Width</label>
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
            <label class="form-control-label" for="post-length">Length</label>
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
            <label class="form-control-label" for="post-direction">Direction</label>
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
            <label class="form-control-label" for="post-distance">Distance</label>
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
            <label class="form-control-label" for="post-legal">Legal</label>
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
            <label class="form-control-label" for="post-numberOfFloors">Number Of Floors</label>
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
            <label class="form-control-label" for="post-numberOfBedroom">Number Of Bedroom</label>
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
            <label class="form-control-label" for="post-hasKitchen">Has Kitchen</label>
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
            <label class="form-control-label" for="post-hasDinningRoom">Has Dinning Room</label>
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
            <label class="form-control-label" for="post-hasRooftop">Has Rooftop</label>
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
            <label class="form-control-label" for="post-hasGarage">Has Garage</label>
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
            <label class="form-control-label" for="post-isVip">Is Vip</label>
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
            <label class="form-control-label" for="post-postingTime">Posting Time</label>
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
            <label class="form-control-label" for="post-expiredTime">Expired Time</label>
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
            <label class="form-control-label" for="post-brokerageFees">Brokerage Fees</label>
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
            <label class="form-control-label" for="post-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.post.status.$invalid, invalid: $v.post.status.$invalid }"
              v-model="$v.post.status.$model"
              id="post-status"
              data-cy="status"
            >
              <option v-for="postStatus in postStatusValues" :key="postStatus" v-bind:value="postStatus">{{ postStatus }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-star">Star</label>
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
            <label class="form-control-label" for="post-hash">Hash</label>
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
            <label class="form-control-label" for="post-type">Type</label>
            <select class="form-control" id="post-type" data-cy="type" name="type" v-model="post.type">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.type && postTypeOption.id === post.type.id ? post.type : postTypeOption"
                v-for="postTypeOption in postTypes"
                :key="postTypeOption.id"
              >
                {{ postTypeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-category">Category</label>
            <select class="form-control" id="post-category" data-cy="category" name="category" v-model="post.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.category && categoryOption.id === post.category.id ? post.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-user">User</label>
            <select class="form-control" id="post-user" data-cy="user" name="user" v-model="post.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.user && userOption.id === post.user.id ? post.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-province">Province</label>
            <select class="form-control" id="post-province" data-cy="province" name="province" v-model="post.province">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.province && provinceOption.id === post.province.id ? post.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-district">District</label>
            <select class="form-control" id="post-district" data-cy="district" name="district" v-model="post.district">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.district && districtOption.id === post.district.id ? post.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-ward">Ward</label>
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
            <label class="form-control-label" for="post-street">Street</label>
            <select class="form-control" id="post-street" data-cy="street" name="street" v-model="post.street">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.street && streetOption.id === post.street.id ? post.street : streetOption"
                v-for="streetOption in streets"
                :key="streetOption.id"
              >
                {{ streetOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.post.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./post-update.component.ts"></script>
