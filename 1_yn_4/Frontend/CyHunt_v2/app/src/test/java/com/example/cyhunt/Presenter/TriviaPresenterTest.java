package com.example.cyhunt.Presenter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.example.cyhunt.Model.ServerRequestTrivia;
import com.example.cyhunt.View.ITriviaView;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TriviaPresenterTest extends TestCase {

    @Mock
    private Context context;

    @Mock
    private ITriviaView view;

    @Mock
    private ServerRequestTrivia model;

    @Test
    public void testLoadTrivia() {
        int i = 1;
        TriviaPresenter presenter = new TriviaPresenter(view, context);
        presenter.setModel(model);

        presenter.loadTrivia(i);

        verify(model, times(1)).getTrivia(i);
    }

    @Test
    public void testUpdateCyscore() {
        int i = 1;
        TriviaPresenter presenter = new TriviaPresenter(view, context);
        presenter.setModel(model);

        presenter.updateCyscore(i);

        verify(model, times(1)).putCyscore(i);
    }

    @Test
    public void testUpdateTriviaAnswered() {
        int i = 1;
        int j = 1;
        TriviaPresenter presenter = new TriviaPresenter(view, context);
        presenter.setModel(model);

        presenter.updateTriviaAnswered(i,j);

        verify(model, times(1)).putTriviaAnswered(i,j);
    }
}