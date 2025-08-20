import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './comment.reducer';

export const CommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commentEntity = useAppSelector(state => state.comment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentDetailsHeading">
          <Translate contentKey="edupressApp.comment.detail.title">Comment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commentEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="edupressApp.comment.content">Content</Translate>
            </span>
          </dt>
          <dd>{commentEntity.content}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="edupressApp.comment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{commentEntity.createdAt ? <TextFormat value={commentEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="edupressApp.comment.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{commentEntity.updatedAt ? <TextFormat value={commentEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="edupressApp.comment.status">Status</Translate>
            </span>
          </dt>
          <dd>{commentEntity.status}</dd>
          <dt>
            <Translate contentKey="edupressApp.comment.lesson">Lesson</Translate>
          </dt>
          <dd>{commentEntity.lesson ? commentEntity.lesson.id : ''}</dd>
          <dt>
            <Translate contentKey="edupressApp.comment.author">Author</Translate>
          </dt>
          <dd>{commentEntity.author ? commentEntity.author.email : ''}</dd>
          <dt>
            <Translate contentKey="edupressApp.comment.parent">Parent</Translate>
          </dt>
          <dd>{commentEntity.parent ? commentEntity.parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comment/${commentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentDetail;
