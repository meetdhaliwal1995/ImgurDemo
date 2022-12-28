package com.example.interviewtask.repository

import com.example.interviewtask.helper.ResultWrapper
import com.example.interviewtask.models.ViralImage
import com.example.interviewtask.retrofit.MyApi
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Sample test cases for Repo
 */
class ImageRepositoryTest {

    @Mock
    lateinit var myApi: MyApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    suspend fun getViralPost() {
        Mockito.`when`(myApi.getViralGallery(0)).thenReturn(Response.success(ViralImage(emptyList(), 200, true)))

        val sut = ImageRepository(myApi)
        val result = sut.getViralPost(0).first()

        Assert.assertEquals(true, result is ResultWrapper.Success)
    }

    @Test
    suspend fun getSearchedData() {
        Mockito.`when`(myApi.getSearchGallery("cats")).thenReturn(Response.success(ViralImage(emptyList(), 200, true)))

        val sut = ImageRepository(myApi)
        val result = sut.getViralPost(0).first()

        Assert.assertEquals(true, result is ResultWrapper.Success)
    }
}