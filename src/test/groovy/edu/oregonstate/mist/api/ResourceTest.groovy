package edu.oregonstate.mist.api

import javax.ws.rs.core.MultivaluedHashMap

import org.junit.Test
import org.junit.Before

@groovy.transform.TypeChecked
class ResourceTest {
    private Resource resource
    private MockUriInfo uriInfo
    private String resourceEndpoint = "test"
    
    @Before
    public void setup() {
        URI uri = new URI('https://api.oregonstate.edu/v1/')
        uriInfo = new MockUriInfo(uri, new MultivaluedHashMap())
        resource = new Resource() {}
        resource.uriInfo = uriInfo
        resource.setEndpointUri(uri)
    }

    @Test
    public void testPaginationUrlNoParams() {
        assert resource.getPaginationUrl([:], resourceEndpoint) ==
                'https://api.oregonstate.edu/v1/' + resourceEndpoint
    }

    @Test
    public void testPaginationUrlWithParams() {
        def params = [
            "q": "hello",
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
                'https://api.oregonstate.edu/v1/test?q=hello'

        params = [
            "a": "1",
            "b": 2,
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
                'https://api.oregonstate.edu/v1/test?a=1&b=2'
    }

    @Test
    public void testPaginationUrlPageNumber() {
        def params = [
            'pageNumber': 2,
            'pageSize': 10,
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
            'https://api.oregonstate.edu/v1/test?page%5Bnumber%5D=2&page%5Bsize%5D=10'

        // Old page numbers are ignored
        params = [
            'page[number]': 2,
            'page[size]': 10,
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
                'https://api.oregonstate.edu/v1/test'

        params = [
            'pageNumber': 2,
            'pageSize': 10,
            'page[number]': 3,
            'page[size]': 9,
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
            'https://api.oregonstate.edu/v1/test?page%5Bnumber%5D=2&page%5Bsize%5D=10'
    }

    @Test
    public void testPaginationUrlNullParams() {
        def params = [
            "a": null,
            "b": 0,
            "c": "",
            "d": 0.0,
            "e": [],
            "pageNumber": 0,
            "pageSize": 0,
        ]
        assert resource.getPaginationUrl(params, resourceEndpoint) ==
                'https://api.oregonstate.edu/v1/test'
    }

    @Test
    public void testPaginationUrlEncoding() {
        // Check that getPaginationUrl correctly encodes its arguments
        assert resource.getPaginationUrl([q: 'this is a test'], resourceEndpoint) ==
            'https://api.oregonstate.edu/v1/test?q=this+is+a+test'
        def lineNoise = '+&= ://?@# \'\"\\'
        assert resource.getPaginationUrl([q: lineNoise], resourceEndpoint) ==
            'https://api.oregonstate.edu/v1/test?q=%2B%26%3D+%3A%2F%2F%3F%40%23+%27%22%5C'
        assert resource.getPaginationUrl([(lineNoise): 'q'], resourceEndpoint) ==
            'https://api.oregonstate.edu/v1/test?%2B%26%3D+%3A%2F%2F%3F%40%23+%27%22%5C=q'
    }

    @Test
    public void testGetPageNumber() {
        // just checking
        assert Resource.DEFAULT_PAGE_NUMBER == 1

        /* Valid values */
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '0']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '1']))
        assert resource.getPageNumber() == 1

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '2']))
        assert resource.getPageNumber() == 2

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '9']))
        assert resource.getPageNumber() == 9

        // one billion
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '1000000000']))
        assert resource.getPageNumber() == 1000000000

        /* Missing or invalid values return the default page number */
        uriInfo.setQueryParameters(new MultivaluedHashMap([:]))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        // ten billion (fails, too large)
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '10000000000']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        // not an integer
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': 'abc']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '2.0']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        // negative number
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '-1']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        // Repeated values default to the first
        def params = new MultivaluedHashMap()
        params.add('page[number]', '2')
        params.add('page[number]', '3')
        uriInfo.setQueryParameters(params)
        assert resource.getPageNumber() == 2
    }

    @Test
    public void testGetPageSize() {
        // just checking
        assert Resource.DEFAULT_PAGE_SIZE == 10

        /* Valid values */
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '0']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '1']))
        assert resource.getPageSize() == 1

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '2']))
        assert resource.getPageSize() == 2

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '9']))
        assert resource.getPageSize() == 9

        // one billion
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '1000000000']))
        assert resource.getPageSize() == 1000000000

        /* Missing or invalid values return the default page size */
        uriInfo.setQueryParameters(new MultivaluedHashMap([:]))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        // not an integer
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': 'abc']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '2.0']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        // ten billion (fails, too large)
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '10000000000']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        // negative number
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[size]': '-1']))
        assert resource.getPageSize() == Resource.DEFAULT_PAGE_SIZE

        // Repeated values default to the first
        def params = new MultivaluedHashMap()
        params.add('page[size]', '2')
        params.add('page[size]', '3')
        uriInfo.setQueryParameters(params)
        assert resource.getPageSize() == 2
    }
}
