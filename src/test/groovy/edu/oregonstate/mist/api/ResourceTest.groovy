package edu.oregonstate.mist.api

import javax.ws.rs.core.MultivaluedHashMap

import org.junit.Test
import org.junit.Before

@groovy.transform.TypeChecked
class ResourceTest {
    private Resource resource

    @Before
    public void setup() {
        URI uri = new URI('https://api.oregonstate.edu/v1/test')
        resource = new Resource() {}
        resource.uriInfo = new MockUriInfo(uri, new MultivaluedHashMap())
        resource.setEndpointUri(uri)
    }
    
    @Test
    public void testPaginationUrlNoParams() {
        assert resource.getPaginationUrl([:]) == 'https://api.oregonstate.edu/v1/test'
    }

    @Test
    public void testPaginationUrlWithParams() {
        def params = [
            "q": "hello",
        ]
        assert resource.getPaginationUrl(params) == 'https://api.oregonstate.edu/v1/test?q=hello'

        params = [
            "a": "1",
            "b": 2,
        ]
        assert resource.getPaginationUrl(params) == 'https://api.oregonstate.edu/v1/test?a=1&b=2'
    }

    @Test
    public void testPaginationUrlPageNumber() {
        def params = [
            'pageNumber': 2,
            'pageSize': 10,
        ]
        assert resource.getPaginationUrl(params) ==
            'https://api.oregonstate.edu/v1/test?page%5Bnumber%5D=2&page%5Bsize%5D=10'

        // Old page numbers are ignored
        params = [
            'page[number]': 2,
            'page[size]': 10,
        ]
        assert resource.getPaginationUrl(params) == 'https://api.oregonstate.edu/v1/test'
        params = [
            'pageNumber': 2,
            'pageSize': 10,
            'page[number]': 3,
            'page[size]': 9,
        ]
        assert resource.getPaginationUrl(params) ==
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
        assert resource.getPaginationUrl(params) == 'https://api.oregonstate.edu/v1/test'
    }

    @Test
    public void testPaginationUrlEncoding() {
        // Check that getPaginationUrl correctly encodes its arguments
        assert resource.getPaginationUrl([q: 'this is a test']) ==
            'https://api.oregonstate.edu/v1/test?q=this+is+a+test'
        def lineNoise = '+&= ://?@# \'\"\\'
        assert resource.getPaginationUrl([q: lineNoise]) ==
            'https://api.oregonstate.edu/v1/test?q=%2B%26%3D+%3A%2F%2F%3F%40%23+%27%22%5C'
        assert resource.getPaginationUrl([(lineNoise): 'q']) ==
            'https://api.oregonstate.edu/v1/test?%2B%26%3D+%3A%2F%2F%3F%40%23+%27%22%5C=q'
    }

    @Test
    public void testGetPageNumber() {
        // just checking
        assert Resource.DEFAULT_PAGE_NUMBER == 1

        /* Missing or invalid values -> default page number */
        MockUriInfo uriInfo = new MockUriInfo(new URI(), new MultivaluedHashMap())
        resource.uriInfo = uriInfo

        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': 'abc']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '2.0']))
        assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        // Fails
        //uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '-1']))
        //assert resource.getPageNumber() == Resource.DEFAULT_PAGE_NUMBER

        /* Valid values */

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '0']))
        assert resource.getPageNumber() == 0

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '1']))
        assert resource.getPageNumber() == 1

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '2']))
        assert resource.getPageNumber() == 2

        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '9']))
        assert resource.getPageNumber() == 9

        // one billion
        uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '1000000000']))
        assert resource.getPageNumber() == 1000000000

        // ten billion (fails)
        //uriInfo.setQueryParameters(new MultivaluedHashMap(['page[number]': '10000000000']))
        //assert resource.getPageNumber() == 10000000000
    }
}
