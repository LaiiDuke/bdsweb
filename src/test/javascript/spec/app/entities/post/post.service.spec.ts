/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import PostService from '@/entities/post/post.service';
import { Post } from '@/shared/model/post.model';
import { PostStatus } from '@/shared/model/enumerations/post-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Post Service', () => {
    let service: PostService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PostService();
      currentDate = new Date();
      elemDefault = new Post(
        123,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        false,
        false,
        false,
        false,
        false,
        currentDate,
        currentDate,
        0,
        PostStatus.WAITING,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            postingTime: dayjs(currentDate).format(DATE_FORMAT),
            expiredTime: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Post', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            postingTime: dayjs(currentDate).format(DATE_FORMAT),
            expiredTime: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            postingTime: currentDate,
            expiredTime: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Post', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Post', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            content: 'BBBBBB',
            price: 1,
            square: 1,
            address: 'BBBBBB',
            googleMapsLocation: 'BBBBBB',
            width: 1,
            length: 1,
            direction: 'BBBBBB',
            distance: 'BBBBBB',
            legal: 'BBBBBB',
            numberOfFloors: 1,
            numberOfBedroom: 1,
            hasKitchen: true,
            hasDinningRoom: true,
            hasRooftop: true,
            hasGarage: true,
            isVip: true,
            postingTime: dayjs(currentDate).format(DATE_FORMAT),
            expiredTime: dayjs(currentDate).format(DATE_FORMAT),
            brokerageFees: 1,
            status: 'BBBBBB',
            star: 1,
            hash: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            postingTime: currentDate,
            expiredTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Post', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Post', async () => {
        const patchObject = Object.assign(
          {
            price: 1,
            address: 'BBBBBB',
            length: 1,
            distance: 'BBBBBB',
            legal: 'BBBBBB',
            numberOfFloors: 1,
            hasDinningRoom: true,
            hasRooftop: true,
            hasGarage: true,
            isVip: true,
            hash: 'BBBBBB',
          },
          new Post()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            postingTime: currentDate,
            expiredTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Post', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Post', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            content: 'BBBBBB',
            price: 1,
            square: 1,
            address: 'BBBBBB',
            googleMapsLocation: 'BBBBBB',
            width: 1,
            length: 1,
            direction: 'BBBBBB',
            distance: 'BBBBBB',
            legal: 'BBBBBB',
            numberOfFloors: 1,
            numberOfBedroom: 1,
            hasKitchen: true,
            hasDinningRoom: true,
            hasRooftop: true,
            hasGarage: true,
            isVip: true,
            postingTime: dayjs(currentDate).format(DATE_FORMAT),
            expiredTime: dayjs(currentDate).format(DATE_FORMAT),
            brokerageFees: 1,
            status: 'BBBBBB',
            star: 1,
            hash: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            postingTime: currentDate,
            expiredTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Post', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Post', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Post', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
