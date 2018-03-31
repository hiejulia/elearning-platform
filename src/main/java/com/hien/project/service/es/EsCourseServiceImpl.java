package com.hien.project.service.es;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hien.project.domain.es.EsCourse;
import com.hien.project.repository.es.EsCourseRepository;
import com.hien.project.service.UserService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;


@Service
public class EsCourseServiceImpl implements EsCourseService {
    //    @Override
//    public Page<Post> findByTagsName(String tagName, PageRequest pageRequest) {
//        return postRepository.findByTagsName(tagName, pageRequest);
//    }


    @Autowired
    private EsCourseRepository esCourseRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserService userService;

    private static final Pageable Top_5_PAGEABLE = new PageRequest(0, 5);

    private static final String   EMPTY_KEYWORD  = "";


    @Override
    public void removeEsCourse(String id) {
        esCourseRepository.delete(id);
    }


    @Override
    public EsCourse updateEsCourse(EsCourse esCourse) {
        return esCourseRepository.save(esCourse);
    }


    @Override
    public EsCourse getEsCourseByCourseId(Long courseId) {
        return esCourseRepository.findByCourseId(courseId);
    }


    @Override
    public Page<EsCourse> listNewestEsCourses(String keyword, Pageable pageable) {
        Page<EsCourse> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        pages = esCourseRepository
                .findDistinctEsCourseByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(
                        keyword, keyword, keyword, keyword, pageable);
        return pages;
    }


    @Override
    public Page<EsCourse> listHotestEsCourses(String keyword, Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize",
                "createTime");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return esCourseRepository
                .findDistinctEsCourseByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(
                        keyword, keyword, keyword, keyword, pageable);
    }


    @Override
    public Page<EsCourse> listEsCourses(Pageable pageable) {
        return esCourseRepository.findAll(pageable);
    }

    
    @Override
    public List<EsCourse> listTop5NewestEsCourses() {
        Page<EsCourse> page = this.listNewestEsCourses(EMPTY_KEYWORD, Top_5_PAGEABLE);
        return page.getContent();
    }

    @Override
    public List<EsCourse> listTop5HotestEsCourses() {
        Page<EsCourse> page = this.listHotestEsCourses(EMPTY_KEYWORD, Top_5_PAGEABLE);
        return page.getContent();
    }


    @Override
    public List<TagVO> listTop30Tags() {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withIndices(indexName).withTypes(typeName)
//                .withQuery(queryBuilder).withAggregation(aggsBuilder)
//                .withSearchType(SearchType.COUNT).build();
        List<TagVO> list = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH).withIndices("course").withTypes("course")
                .addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
                .build();
        Aggregations aggregations=elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("tags");

        Iterator<Bucket> modelBucketIt=modelTerms.getBuckets().iterator();
        while(modelBucketIt.hasNext()){
            Bucket actiontypeBucket = modelBucketIt.next();

            list.add(new TagVO(actiontypeBucket.getKey().toString(),actiontypeBucket.getDocCount()));
        }
        return list;
    }


    @Override
    public List<User> listTop12Users() {
        List<String> usernamelist = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("course").withTypes("course")
                .addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("users");

        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            String username = actiontypeBucket.getKey().toString();
            usernamelist.add(username);
        }
        List<User> list = userService.listUsersByUsernames(usernamelist);
        return list;
    }


}
