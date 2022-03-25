import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

class RandomWordsUI extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: "RandomWords",
        theme: ThemeData(primarySwatch: Colors.blue),
        home: Page());
  }
}

class Page extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return RandomWordsState();
  }
}

class RandomWordsState extends State {
  final _suggestions = <WordPair>[];
  final _biggerFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('RandomWords'),
      ),
      body: _buildSuggestions(),
    );
  }

  Widget _buildSuggestions() {
    return ListView.builder(
      padding: const EdgeInsets.all(16.0),
      itemBuilder: (context, i) {
        if (i.isOdd) return const Divider();
        final index = i ~/ 2;
        if (index >= _suggestions.length) {
          _suggestions.addAll(generateWordPairs().take(10));
        }
        return _buildRow(_suggestions[index]);
      },
    );
  }

  Widget _buildRow(WordPair pair) {
    return ListTile(
      title: TextButton(
        child: Text(
          pair.asPascalCase,
          style: _biggerFont,
        ),
        onPressed: null,
      ),
    );
  }
}
